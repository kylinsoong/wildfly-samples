package com.acme.corp.tracker.extension;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.jboss.as.controller.PathAddress;
import org.jboss.as.controller.PathElement;
import org.jboss.as.subsystem.test.AbstractSubsystemTest;
import org.jboss.as.subsystem.test.AdditionalInitialization;
import org.jboss.as.subsystem.test.KernelServices;
import org.jboss.dmr.ModelNode;
import org.junit.Test;

import java.util.List;

import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.ADD;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.DESCRIBE;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.NAME;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.OP;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.OP_ADDR;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.OUTCOME;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.READ_ATTRIBUTE_OPERATION;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.SUBSYSTEM;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.SUCCESS;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.VALUE;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.WRITE_ATTRIBUTE_OPERATION;

public class SubsystemParsingTestCase extends AbstractSubsystemTest {

    public SubsystemParsingTestCase() {
        super(TrackerExtension.SUBSYSTEM_NAME, new TrackerExtension());
    }
    
    static String SUBSYSTEMXML =
            "<subsystem xmlns=\"" + TrackerExtension.NAMESPACE_1_0 + "\">" +
            "   <deployment-types>" +
            "       <deployment-type suffix=\"war\" tick=\"10000\"/>" +
            "   </deployment-types>" +
            "</subsystem>";

    /**
     * Tests that the xml is parsed into the correct operations
     */
    @Test
    public void testParseSubsystem() throws Exception {
        
        List<ModelNode> operations = super.parse(SUBSYSTEMXML);

        ///Check that we have the expected number of operations
       assertEquals(2, operations.size());

        //Check that each operation has the correct content
        //The add subsystem operation will happen first
        ModelNode addSubsystem = operations.get(0);
        assertEquals(ADD, addSubsystem.get(OP).asString());
        PathAddress addr = PathAddress.pathAddress(addSubsystem.get(OP_ADDR));
        assertEquals(1, addr.size());
        PathElement element = addr.getElement(0);
        assertEquals(SUBSYSTEM, element.getKey());
        assertEquals(TrackerExtension.SUBSYSTEM_NAME, element.getValue());

        //Then we will get the add type operation
        ModelNode addType = operations.get(1);
        assertEquals(ADD, addType.get(OP).asString());
        assertEquals(10000, addType.get("tick").asLong());
        addr = PathAddress.pathAddress(addType.get(OP_ADDR));
        assertEquals(2, addr.size());
        element = addr.getElement(0);
        assertEquals(SUBSYSTEM, element.getKey());
        assertEquals(TrackerExtension.SUBSYSTEM_NAME, element.getValue());
        element = addr.getElement(1);
        assertEquals("type", element.getKey());
        assertEquals("war", element.getValue());
    }

    /**
     * Test that the model created from the xml looks as expected
     */
    @Test
    public void testInstallIntoController() throws Exception {

        KernelServices services = createKernelServicesBuilder(new AdditionalInitialization()).setSubsystemXml(SUBSYSTEMXML).build();
        //Read the whole model and make sure it looks as expected
        ModelNode model = services.readWholeModel();
        
        assertTrue(model.get(SUBSYSTEM).hasDefined(TrackerExtension.SUBSYSTEM_NAME));
        assertTrue(model.get(SUBSYSTEM, TrackerExtension.SUBSYSTEM_NAME).hasDefined("type"));
        assertTrue(model.get(SUBSYSTEM, TrackerExtension.SUBSYSTEM_NAME, "type").hasDefined("war"));
        assertTrue(model.get(SUBSYSTEM, TrackerExtension.SUBSYSTEM_NAME, "type", "war").hasDefined("tick"));
        assertEquals(10000, model.get(SUBSYSTEM, TrackerExtension.SUBSYSTEM_NAME, "type", "war", "tick").asLong());
    }

    /**
     * Starts a controller with a given subsystem xml and then checks that a second
     * controller started with the xml marshalled from the first one results in the same model
     */
    @Test
    public void testParseAndMarshalModel() throws Exception {
        
        KernelServices servicesA = createKernelServicesBuilder(new AdditionalInitialization()).setSubsystemXml(SUBSYSTEMXML).build();
        //Get the model and the persisted xml from the first controller
        ModelNode modelA = servicesA.readWholeModel();
        String marshalled = servicesA.getPersistedSubsystemXml();

        //Install the persisted xml from the first controller into a second controller
        KernelServices servicesB = createKernelServicesBuilder(new AdditionalInitialization()).setSubsystemXml(marshalled).build();
        ModelNode modelB = servicesB.readWholeModel();

        //Make sure the models from the two controllers are identical
        compare(modelA, modelB);
    }

    /**
     * Starts a controller with the given subsystem xml and then checks that a second
     * controller started with the operations from its describe action results in the same model
     */
    @Test
    public void testDescribeHandler() throws Exception {
        
        KernelServices servicesA = createKernelServicesBuilder(new AdditionalInitialization()).setSubsystemXml(SUBSYSTEMXML).build();
        //Get the model and the describe operations from the first controller
        ModelNode modelA = servicesA.readWholeModel();
        ModelNode describeOp = new ModelNode();
        describeOp.get(OP).set(DESCRIBE);
        describeOp.get(OP_ADDR).set(PathAddress.pathAddress(PathElement.pathElement(SUBSYSTEM, TrackerExtension.SUBSYSTEM_NAME)).toModelNode());
        List<ModelNode> operations = checkResultAndGetContents(servicesA.executeOperation(describeOp)).asList();

        //Install the describe options from the first controller into a second controller
        KernelServices servicesB = createKernelServicesBuilder(new AdditionalInitialization()).setBootOperations(operations).build();
        ModelNode modelB = servicesB.readWholeModel();

        //Make sure the models from the two controllers are identical
        compare(modelA, modelB);

    }

    /**
     * Tests that the subsystem can be removed
     */
    @Test
    public void testSubsystemRemoval() throws Exception {

        KernelServices services = createKernelServicesBuilder(new AdditionalInitialization()).setSubsystemXml(SUBSYSTEMXML).build();

        //Sanity check to test the service for 'war' was there
        services.getContainer().getRequiredService(TrackerService.createServiceName("war"));

        //Checks that the subsystem was removed from the model
        assertRemoveSubsystemResources(services);

        //Check that any services that were installed were removed here
        try {
            services.getContainer().getRequiredService(TrackerService.createServiceName("war"));
            fail("Should have removed services");
        } catch (Exception expected) {
        }
    }

    @Test
    public void testExecuteOperations() throws Exception {

        KernelServices services = createKernelServicesBuilder(new AdditionalInitialization()).setSubsystemXml(SUBSYSTEMXML).build();

        //Add another type
        PathAddress fooTypeAddr = PathAddress.pathAddress(PathElement.pathElement(SUBSYSTEM, TrackerExtension.SUBSYSTEM_NAME),PathElement.pathElement("type", "foo"));
        ModelNode addOp = new ModelNode();
        addOp.get(OP).set(ADD);
        addOp.get(OP_ADDR).set(fooTypeAddr.toModelNode());
        addOp.get("tick").set(1000);
        ModelNode result = services.executeOperation(addOp);
        assertEquals(SUCCESS, result.get(OUTCOME).asString());


        ModelNode model = services.readWholeModel();
        assertTrue(model.get(SUBSYSTEM).hasDefined(TrackerExtension.SUBSYSTEM_NAME));
        assertTrue(model.get(SUBSYSTEM, TrackerExtension.SUBSYSTEM_NAME).hasDefined("type"));
        assertTrue(model.get(SUBSYSTEM, TrackerExtension.SUBSYSTEM_NAME, "type").hasDefined("war"));
        assertTrue(model.get(SUBSYSTEM, TrackerExtension.SUBSYSTEM_NAME, "type", "war").hasDefined("tick"));
        assertEquals(10000, model.get(SUBSYSTEM, TrackerExtension.SUBSYSTEM_NAME, "type", "war", "tick").asLong());

        assertTrue(model.get(SUBSYSTEM, TrackerExtension.SUBSYSTEM_NAME, "type").hasDefined("foo"));
        assertTrue(model.get(SUBSYSTEM, TrackerExtension.SUBSYSTEM_NAME, "type", "foo").hasDefined("tick"));
        assertEquals(1000, model.get(SUBSYSTEM, TrackerExtension.SUBSYSTEM_NAME, "type", "foo", "tick").asLong());

        //Call write-attribute
        ModelNode writeOp = new ModelNode();
        writeOp.get(OP).set(WRITE_ATTRIBUTE_OPERATION);
        writeOp.get(OP_ADDR).set(fooTypeAddr.toModelNode());
        writeOp.get(NAME).set("tick");
        writeOp.get(VALUE).set(3456);
        result = services.executeOperation(writeOp);
        assertEquals(SUCCESS, result.get(OUTCOME).asString());

        //Check that write attribute took effect, this time by calling read-attribute instead of reading the whole model
        ModelNode readOp = new ModelNode();
        readOp.get(OP).set(READ_ATTRIBUTE_OPERATION);
        readOp.get(OP_ADDR).set(fooTypeAddr.toModelNode());
        readOp.get(NAME).set("tick");
        result = services.executeOperation(readOp);
        assertEquals(3456, checkResultAndGetContents(result).asLong());

        TrackerService service = (TrackerService) services.getContainer().getService(TrackerService.createServiceName("foo")).getValue();
        assertEquals(3456, service.getTick());
    }
}
