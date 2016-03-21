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
import org.jboss.as.console.client.acme.model.SubsystemConfiguration;
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


public class SubsystemPresenter extends Presenter<SubsystemPresenter.TrackerView, SubsystemPresenter.TrackerProxy> implements Persistable<SubsystemConfiguration> {
    
    private DispatchAsync dispatcher;
    private RevealStrategy revealStrategy;
    
    private EntityAdapter<SubsystemConfiguration> configurationEntityAdapter;
    
    public interface TrackerView extends View {
        void setPresenter(SubsystemPresenter presenter);
        void setConfigurationBean(SubsystemConfiguration bean);
    }
    
    @ProxyCodeSplit
    @NameToken("tracker")
    @SubsystemExtension(name="Tracker", group = "Tracker", key="tracker")
    @RequiredResources(resources = {"{selected.profile}/subsystem=tracker"})
    @SearchIndex(keywords = {"tracker", "tick"})  
    public interface TrackerProxy extends Proxy<SubsystemPresenter>, Place {
    }
    
    @Inject
    public SubsystemPresenter(EventBus eventBus, TrackerView view, TrackerProxy proxy, DispatchAsync dispatcher, RevealStrategy revealStrategy, ApplicationMetaData metadata) {
        super(eventBus, view, proxy);

        this.dispatcher = dispatcher;
        this.revealStrategy = revealStrategy;

        this.configurationEntityAdapter = new EntityAdapter<>(SubsystemConfiguration.class, metadata);
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
        loadConfigurationModel();
    }
    
    private void loadConfigurationModel() {
        ModelNode operation = new ModelNode();
        operation.get(OP).set(READ_RESOURCE_OPERATION);
        operation.get(ADDRESS).set(Baseadress.get());
        operation.get(ADDRESS).add("subsystem", "tracker");
        operation.get(INCLUDE_RUNTIME).set(true);
        
        dispatcher.execute(new DMRAction(operation), new SimpleCallback<DMRResponse>() {
            @Override
            public void onSuccess(DMRResponse dmrResponse) {
                ModelNode response = dmrResponse.get();
                SubsystemConfiguration bean = configurationEntityAdapter.fromDMR(response.get(RESULT));
                getView().setConfigurationBean(bean);
            }
            @Override
            public void onFailure(Throwable caught) {
                Console.error("Failed to retrieve configuration for Tracker subsystem", caught.getMessage());
            }             
        });
    }

    @Override
    public void save(SubsystemConfiguration t, Map<String, Object> changeset) {
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
                    Console.error(Console.MESSAGES.saveFailed("Tracker configuration modification failed"),
                            response.getFailureDescription());
                }
                loadConfigurationModel();
            }
            @Override
            public void onFailure(Throwable caught) {
                super.onFailure(caught);
                loadConfigurationModel();
            }            
        }); 
    }

}
