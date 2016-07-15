package com.acme.corp.tracker.client.runtime;

import static org.jboss.dmr.client.ModelDescriptionConstants.OP;
import static org.jboss.dmr.client.ModelDescriptionConstants.ADDRESS;
import static org.jboss.dmr.client.ModelDescriptionConstants.RESULT;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.jboss.as.console.client.Console;
import org.jboss.as.console.client.core.MainLayoutPresenter;
import org.jboss.as.console.client.domain.model.SimpleCallback;
import org.jboss.as.console.client.shared.runtime.RuntimeBaseAddress;
import org.jboss.as.console.client.v3.ResourceDescriptionRegistry;
import org.jboss.as.console.spi.RequiredResources;
import org.jboss.as.console.spi.RuntimeExtension;
import org.jboss.dmr.client.ModelNode;
import org.jboss.dmr.client.dispatch.DispatchAsync;
import org.jboss.dmr.client.dispatch.impl.DMRAction;
import org.jboss.dmr.client.dispatch.impl.DMRResponse;
import org.useware.kernel.gui.behaviour.StatementContext;

import com.acme.corp.tracker.client.i18n.I18n;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

public class DeploymentPresenter extends Presenter<DeploymentPresenter.MyView, DeploymentPresenter.MyProxy>{
      
    @ProxyCodeSplit
    @NameToken("tracker-deployments")
    @RuntimeExtension(name="Tracker", key="tracker")
    @RequiredResources(resources = {"{selected.profile}/subsystem=tracker"})
    public interface MyProxy extends ProxyPlace<DeploymentPresenter> {}
    
    public interface MyView extends View {
        void setPresenter(DeploymentPresenter presenter);
        void setDeployments(List<String> depoyments, boolean isCool);
    }
    
    private DispatchAsync dispatcher;
    private I18n i18n;
    
    private static final String OP_LOAD_DEPLOYMENT = "list-deployments";
    private static final String OP_LOAD_COOL_DEPLOYMENT = "list-cool-deployments";
    private static final List<String> EMPTY_LIST = new ArrayList<String>(0);
        
    @Inject
    public DeploymentPresenter(EventBus eventBus
                             , MyView view
                             , MyProxy proxy
                             , StatementContext statementContext
                             , DispatchAsync dispatcher
                             , I18n i18n
                             , ResourceDescriptionRegistry descriptionRegistry) {
        
        super(eventBus, view, proxy, MainLayoutPresenter.TYPE_MainContent);
        this.dispatcher = dispatcher;
        this.i18n = i18n;
    }

    @Override
    protected void onBind() {
        super.onBind();
        getView().setPresenter(this);
    }
    
    @Override
    protected void onReset() {
        super.onReset();
        loadDeployments();
    } 
    
    public void loadDeployments() {
        reloadDeployments(true);
        reloadDeployments(false);
        
    }

    public void reloadDeployments(boolean isCool) {
        
        final ModelNode address = RuntimeBaseAddress.get();
        address.add("subsystem", "tracker");

        if(isCool) {
            
            getView().setDeployments(EMPTY_LIST, true);
            
            ModelNode operation = new ModelNode();
            operation.get(OP).set(OP_LOAD_COOL_DEPLOYMENT);
            operation.get(ADDRESS).set(address);
            
            dispatcher.execute(new DMRAction(operation), new SimpleCallback<DMRResponse>(){

                @Override
                public void onSuccess(DMRResponse response) {
                    ModelNode body = response.get();
                    if (body.isFailure()) {
                        Console.error(i18n.extensionConstants().load_cool_deployment_failed(), body.getFailureDescription());
                    } else {
                        getView().setDeployments(formDeployments(body.get(RESULT).asList()), true);
                    }
                }
                });
        } else {
            
            getView().setDeployments(EMPTY_LIST, false);
            
            ModelNode operation = new ModelNode();
            operation.get(OP).set(OP_LOAD_DEPLOYMENT);
            operation.get(ADDRESS).set(address);
            
            dispatcher.execute(new DMRAction(operation), new SimpleCallback<DMRResponse>(){

                @Override
                public void onSuccess(DMRResponse response) {
                    ModelNode body = response.get();
                    if (body.isFailure()) {
                        Console.error(i18n.extensionConstants().load_deployment_failed(), body.getFailureDescription());
                    } else {
                        getView().setDeployments(formDeployments(body.get(RESULT).asList()), false);
                    }
                }
                });
        } 
    }
    
    private List<String> formDeployments(List<ModelNode> nodelist) {
        List<String> deployments = new ArrayList<String>(nodelist.size());
        for(ModelNode node : nodelist){
            deployments.add(node.asString());
        }
        return deployments;
    }
    
}
