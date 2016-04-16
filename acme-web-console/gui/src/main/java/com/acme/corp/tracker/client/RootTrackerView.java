package com.acme.corp.tracker.client;

import static org.jboss.dmr.client.ModelDescriptionConstants.DESCRIPTION;

import java.util.Map;

import org.jboss.as.console.client.layout.OneToOneLayout;
import org.jboss.as.console.client.rbac.SecurityFramework;
import org.jboss.as.console.client.v3.ResourceDescriptionRegistry;
import org.jboss.as.console.client.v3.dmr.AddressTemplate;
import org.jboss.as.console.client.v3.dmr.ResourceDescription;
import org.jboss.as.console.mbui.widgets.ModelNodeForm;
import org.jboss.as.console.mbui.widgets.ModelNodeFormBuilder;
import org.jboss.ballroom.client.rbac.SecurityContext;
import org.jboss.ballroom.client.widgets.forms.FormCallback;
import org.jboss.dmr.client.ModelNode;

import com.google.gwt.user.client.ui.Widget;

public class RootTrackerView {
    
    final static AddressTemplate ADDRESS = AddressTemplate.of("{selected.profile}/subsystem=tracker");
    
    private ExtensionPresenter presenter;
    private SecurityFramework securityFramework;
    private ResourceDescriptionRegistry descriptionRegistry;
    private ModelNodeForm form;
    
    public RootTrackerView(ExtensionPresenter presenter, SecurityFramework securityFramework, ResourceDescriptionRegistry descriptionRegistry) {
        super();
        this.presenter = presenter;
        this.securityFramework = securityFramework; 
        this.descriptionRegistry = descriptionRegistry;
    }
    
    public void setData(ModelNode data){
        form.edit(data);
    }
    
    @SuppressWarnings("rawtypes")
    public Widget asWidget() {
        SecurityContext securityContext = securityFramework.getSecurityContext(presenter.getProxy().getNameToken());
//        ResourceDescription resourceDescription = descriptionRegistry.lookup(
//                ExtensionPresenter.ROOT_RESOURCE_ADDRESS);
        
        final ResourceDescription definition = descriptionRegistry.lookup(ADDRESS);

        ModelNodeFormBuilder.FormAssets formAssets = new ModelNodeFormBuilder()
                .setConfigOnly()
                .setResourceDescription(definition)
                .setSecurityContext(securityContext)
                .build();

        form = formAssets.getForm();
        
        FormCallback callback = new FormCallback(){

            @SuppressWarnings("unchecked")
            @Override
            public void onSave(Map changedValues) {
                presenter.onSaveResource(changedValues);
            }

            @Override
            public void onCancel(Object entity) {
                form.cancel();
            }};
            
        form.setToolsCallback(callback);

        return new OneToOneLayout()
                .setPlain(true)
                .setHeadline("Tracker")
                .setDescription(definition.get(DESCRIPTION).asString())
                .addDetail("Attributes", formAssets.asWidget())
                .build();
    }
}
