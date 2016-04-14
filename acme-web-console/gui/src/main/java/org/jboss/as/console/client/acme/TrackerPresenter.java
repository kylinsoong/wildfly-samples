  package org.jboss.as.console.client.acme;

import static org.jboss.dmr.client.ModelDescriptionConstants.ADDRESS;
import static org.jboss.dmr.client.ModelDescriptionConstants.INCLUDE_RUNTIME;
import static org.jboss.dmr.client.ModelDescriptionConstants.OP;
import static org.jboss.dmr.client.ModelDescriptionConstants.OUTCOME;
import static org.jboss.dmr.client.ModelDescriptionConstants.READ_RESOURCE_OPERATION;
import static org.jboss.dmr.client.ModelDescriptionConstants.RESULT;
import static org.jboss.dmr.client.ModelDescriptionConstants.SUCCESS;
import static org.jboss.dmr.client.ModelDescriptionConstants.WRITE_ATTRIBUTE_OPERATION;

import java.util.Map;

import org.jboss.as.console.client.Console;
import org.jboss.as.console.client.acme.model.TrackerSubsystem;
import org.jboss.as.console.client.domain.model.SimpleCallback;
import org.jboss.as.console.client.shared.subsys.Baseadress;
import org.jboss.as.console.client.shared.subsys.RevealStrategy;
import org.jboss.as.console.client.widgets.forms.ApplicationMetaData;
import org.jboss.as.console.client.widgets.forms.EntityAdapter;
import org.jboss.as.console.spi.RequiredResources;
import org.jboss.as.console.spi.SearchIndex;
import org.jboss.as.console.spi.SubsystemExtension;
import org.jboss.dmr.client.ModelNode;
import org.jboss.dmr.client.dispatch.DispatchAsync;
import org.jboss.dmr.client.dispatch.impl.DMRAction;
import org.jboss.dmr.client.dispatch.impl.DMRResponse;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.Place;
import com.gwtplatform.mvp.client.proxy.Proxy;


public class TrackerPresenter extends Presenter<TrackerPresenter.MyView, TrackerPresenter.MyProxy> implements Persistable<TrackerSubsystem> {
    
    @ProxyCodeSplit
    @NameToken("tracker")
    @SearchIndex(keywords = {"tracker", "tick"})  
    @SubsystemExtension(name="Tracker", group = "Tracker", key="tracker")
    @RequiredResources(resources = {"{selected.profile}/subsystem=tracker"})
    public interface MyProxy extends Proxy<TrackerPresenter>, Place {
    }
    
    public interface MyView extends View {
        void setPresenter(TrackerPresenter presenter);
        void updateFrom(TrackerSubsystem bean);
    }
    
    private DispatchAsync dispatcher;
    private RevealStrategy revealStrategy;
    
    private EntityAdapter<TrackerSubsystem> configurationEntityAdapter;
    
    @Inject
    public TrackerPresenter(EventBus eventBus, 
                            MyView view, 
                            MyProxy proxy, 
                            DispatchAsync dispatcher, 
                            RevealStrategy revealStrategy, 
                            ApplicationMetaData metadata) {
        super(eventBus, view, proxy);

        this.dispatcher = dispatcher;
        this.revealStrategy = revealStrategy;

        this.configurationEntityAdapter = new EntityAdapter<>(TrackerSubsystem.class, metadata);
    }

    @Override
    protected void onBind() {
        super.onBind();
        getView().setPresenter(this);
    }
    
    @Override
    protected void revealInParent() {
        revealStrategy.revealInParent(this);
    }
    
    @Override
    protected void onReset() {
        super.onReset();
        loadSubsystem();
    }
    
    private void loadSubsystem() {
        ModelNode operation = new ModelNode();
        operation.get(OP).set(READ_RESOURCE_OPERATION);
        operation.get(ADDRESS).set(Baseadress.get());
        operation.get(ADDRESS).add("subsystem", "tracker");
        operation.get(INCLUDE_RUNTIME).set(true);
        
        dispatcher.execute(new DMRAction(operation), new SimpleCallback<DMRResponse>() {
            @Override
            public void onSuccess(DMRResponse dmrResponse) {
                ModelNode response = dmrResponse.get();
                TrackerSubsystem bean = configurationEntityAdapter.fromDMR(response.get(RESULT));
                getView().updateFrom(bean);
            }
            @Override
            public void onFailure(Throwable caught) {
                Console.error("Failed to retrieve configuration for Tracker subsystem", caught.getMessage());
            }             
        });
    }

    @Override
    public void save(TrackerSubsystem t, Map<String, Object> changeset) {
        ModelNode address = new ModelNode();
        address.get(ADDRESS).set(Baseadress.get());
        address.get(ADDRESS).add("subsystem", "tracker");
        address.get(OP).set(WRITE_ATTRIBUTE_OPERATION);

        ModelNode operation = this.configurationEntityAdapter.fromChangeset(changeset, address);

        dispatcher.execute(new DMRAction(operation), new SimpleCallback<DMRResponse>() {
            @Override
            public void onSuccess(DMRResponse result) {
                ModelNode response = result.get();
                boolean success = response.get(OUTCOME).asString().equals(SUCCESS);

                if (success) {
                    Console.info(Console.MESSAGES.saved("Tracker configuration modified"));
                } else {
                    Console.error(Console.MESSAGES.saveFailed("Tracker configuration modification failed"), response.getFailureDescription());
                }
                loadSubsystem();
            }
            @Override
            public void onFailure(Throwable caught) {
                super.onFailure(caught);
                loadSubsystem();
            }            
        }); 
    }

}
