# 概述

本文根据社区文档 [Extending JBoss AS 7](https://docs.jboss.org/author/display/AS72/Example+subsystem) 扩展 JBoss 7。通过本文我们可以更好的理解 JBoss 底层，本文包括如下内容：

* 创建初始工程
* 创建 schema
* 设计定义 XML 模型
* 注册核心 subsystem 模型
* 注册子 subsystem

# 创建初始工程

通过如下 mvn 命令创建初始化工程：

~~~
mvn archetype:generate -DarchetypeArtifactId=jboss-as-subsystem -DarchetypeGroupId=org.jboss.as.archetypes -DarchetypeVersion=7.1.1.Final -DarchetypeRepository=http://repository.jboss.org/nexus/content/groups/public
~~~

mvn 会提示输入相关的信息，如下所示：

~~~
Define value for property 'groupId': : com.acme.corp
Define value for property 'artifactId': : acme-subsystem
Define value for property 'version':  1.0-SNAPSHOT: : 1.0-SNAPSHOT
Define value for property 'package':  com.acme.corp: : com.acme.corp.tracker
Define value for property 'module': : com.acme.corp.tracker
~~~

mvn 命令执行成功后会产生 acme-subsystem 为初始化的项目，我们可以编译将该项目导入 eclipse 方便后续步骤。通过 mvn install 编译此项目会有如下错误：

~~~
Tests in error: 
  testInstallIntoController(com.acme.corp.tracker.extension.SubsystemParsingTestCase): Can't find bundle for base name com.acme.corp.tracker.extension.LocalDescriptions, locale en_US
  testParseAndMarshalModel(com.acme.corp.tracker.extension.SubsystemParsingTestCase): Can't find bundle for base name com.acme.corp.tracker.extension.LocalDescriptions, locale en_US
  testDescribeHandler(com.acme.corp.tracker.extension.SubsystemParsingTestCase): Can't find bundle for base name com.acme.corp.tracker.extension.LocalDescriptions, locale en_US
  testSubsystemRemoval(com.acme.corp.tracker.extension.SubsystemParsingTestCase): Can't find bundle for base name com.acme.corp.tracker.extension.LocalDescriptions, locale en_US
  testSubsystem(com.acme.corp.tracker.extension.SubsystemBaseParsingTestCase): Can't find bundle for base name com.acme.corp.tracker.extension.LocalDescriptions, locale en_US
~~~

解决此问题的方法是移动 `src/main/resources/com/acme/corp/tracker/subsystem/extension/LocalDescriptions.properties` 到 `src/main/resources/com/acme/corp/tracker/extension/LocalDescriptions.properties`

# 创建 schema

重命名 `src/main/resources/schema/mysubsystem.xsd` 为 `src/main/resources/schema/acme.xsd`，编辑 `acme.xsd` 内容如下：

~~~
<xs:schema xmlns:xs="http://www.w3.org/2001/XMLSchema"
            targetNamespace="urn:com.acme.corp.tracker:1.0"
            xmlns="urn:com.acme.corp.tracker:1.0"
            elementFormDefault="qualified"
            attributeFormDefault="unqualified"
            version="1.0">
 
   <!-- The subsystem root element -->
   <xs:element name="subsystem" type="subsystemType"/>
   <xs:complexType name="subsystemType">
      <xs:all>
         <xs:element name="deployment-types" type="deployment-typesType"/>
      </xs:all>
   </xs:complexType>
   <xs:complexType name="deployment-typesType">
      <xs:choice minOccurs="0" maxOccurs="unbounded">
         <xs:element name="deployment-type" type="deployment-typeType"/>
      </xs:choice>
   </xs:complexType>
   <xs:complexType name="deployment-typeType">
      <xs:attribute name="suffix" use="required"/>
      <xs:attribute name="tick" type="xs:long" use="optional" default="10000"/>
   </xs:complexType>
</xs:schema>
~~~

编辑 `com.acme.corp.tracker.extension.SubsystemExtension`，确保 NAMESPACE 与 schema 中定义的对应：

~~~
public class SubsystemExtension implements Extension {

    /**
     * The name space used for the {@code substystem} element
     */
    public static final String NAMESPACE = "urn:com.acme.corp.tracker:1.0";
~~~

# 设计定义 XML 模型

如下包括一个 XML 配置模型：

~~~
<subsystem xmlns="urn:com.acme.corp.tracker:1.0">
   <deployment-types>
      <deployment-type suffix="sar" tick="10000"/>
      <deployment-type suffix="war" tick="10000"/>
   </deployment-types>
</subsystem>
~~~

我们设计当我们执行 `:read-resource(recursive=true)` 时会有如下输出：

~~~
{
    "outcome" => "success",
    "result" => {"type" => {
        "sar" => {"tick" => "10000"},
        "war" => {"tick" => "10000"}
    }}
}
~~~

编辑 `com.acme.corp.tracker.extension.SubsystemExtension`，确保 SUBSYSTEM_NAME 值为 tracker：

~~~
public class SubsystemExtension implements Extension {
    ...
    /** The name of our subsystem within the model. */
    public static final String SUBSYSTEM_NAME = "tracker";
    ...
~~~

SubsystemExtension.initialize() 方法展示了如何定义模型，如下：

~~~
    public void initialize(ExtensionContext context) {
        final SubsystemRegistration subsystem = context.registerSubsystem(SUBSYSTEM_NAME, 1, 0);
        final ManagementResourceRegistration registration = subsystem.registerSubsystemModel(SubsystemDefinition.INSTANCE);
        registration.registerOperationHandler(DESCRIBE, GenericSubsystemDescribeHandler.INSTANCE, GenericSubsystemDescribeHandler.INSTANCE, false, OperationEntry.EntryType.PRIVATE);

        subsystem.registerXMLElementWriter(parser);
    }
~~~

随后我们会继续说明此方法，并修改此方法，添加更多的内容。

# 注册核心 subsystem 模型

如上面 SubsystemExtension.initialize() 方法展示，首先我们获取 SubsystemRegistration 通过 extension 上下文，接着我们获取 ManagementResourceRegistration，extension 相关的编程，这一步是必须的。

~~~
final ManagementResourceRegistration registration = subsystem.registerSubsystemModel(SubsystemDefinition.INSTANCE);
~~~

传入的参数 SubsystemDefinition 它实现了 org.jboss.as.controller.ResourceDefinition 接口，调运 /subsystem=tracker:read-resource-description 输出的数据模型是在 SubsystemDefinition.INSTANCE 中定义的。如下为 SubsystemDefinition 明细：

~~~
public class SubsystemDefinition extends SimpleResourceDefinition {
    public static final SubsystemDefinition INSTANCE = new SubsystemDefinition();
 
    private SubsystemDefinition() {
        super(SubsystemExtension.SUBSYSTEM_PATH,
                SubsystemExtension.getResourceDescriptionResolver(null),
                //We always need to add an 'add' operation
                SubsystemAdd.INSTANCE,
                //Every resource that is added, normally needs a remove operation
                SubsystemRemove.INSTANCE);
    }
 
    @Override
    public void registerOperations(ManagementResourceRegistration resourceRegistration) {
        super.registerOperations(resourceRegistration);
        //you can register aditional operations here
    }
 
    @Override
    public void registerAttributes(ManagementResourceRegistration resourceRegistration) {
        //you can register attributes here
    }
}
~~~

如上构造方法中的 ADD 和 REMOVE handler 实现如下：

~~~
class SubsystemAdd extends AbstractBoottimeAddStepHandler {

    static final SubsystemAdd INSTANCE = new SubsystemAdd();

    private final Logger log = Logger.getLogger(SubsystemAdd.class);

    private SubsystemAdd() {
    }

    /** {@inheritDoc} */
    @Override
    protected void populateModel(ModelNode operation, ModelNode model) throws OperationFailedException {
        log.info("Populating the model");
//        model.setEmptyObject();
        model.get("type").setEmptyObject();
    }

    /** {@inheritDoc} */
    @Override
    public void performBoottime(OperationContext context, ModelNode operation, ModelNode model,
            ServiceVerificationHandler verificationHandler, List<ServiceController<?>> newControllers)
            throws OperationFailedException {

        //Add deployment processors here
        //Remove this if you don't need to hook into the deployers, or you can add as many as you like
        //see SubDeploymentProcessor for explanation of the phases
        context.addStep(new AbstractDeploymentChainStep() {
            public void execute(DeploymentProcessorTarget processorTarget) {
                processorTarget.addDeploymentProcessor(SubsystemDeploymentProcessor.PHASE, SubsystemDeploymentProcessor.PRIORITY, new SubsystemDeploymentProcessor());

            }
        }, OperationContext.Stage.RUNTIME);

    }
}
~~~


~~~
class SubsystemRemove extends AbstractRemoveStepHandler {

    static final SubsystemRemove INSTANCE = new SubsystemRemove();

    private final Logger log = Logger.getLogger(SubsystemRemove.class);

    private SubsystemRemove() {
    }

    @Override
    protected void performRuntime(OperationContext context, ModelNode operation, ModelNode model) throws OperationFailedException {
        //Remove any services installed by the corresponding add handler here
        //context.removeService(ServiceName.of("some", "name"));
    }


}
~~~

# 注册子 subsystem


