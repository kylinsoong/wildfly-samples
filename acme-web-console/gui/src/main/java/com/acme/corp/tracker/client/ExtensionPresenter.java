package com.acme.corp.tracker.client;

import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

import org.jboss.as.console.client.Console;
import org.jboss.as.console.client.core.HasPresenter;
import org.jboss.as.console.client.core.MainLayoutPresenter;
import org.jboss.as.console.client.domain.model.SimpleCallback;
import org.jboss.as.console.client.shared.subsys.Baseadress;
import org.jboss.as.console.client.v3.ResourceDescriptionRegistry;
import org.jboss.as.console.client.v3.behaviour.CrudOperationDelegate;
import org.jboss.as.console.client.v3.dmr.AddressTemplate;
import org.jboss.as.console.client.v3.widgets.AddResourceDialog;
import org.jboss.as.console.spi.RequiredResources;
import org.jboss.as.console.spi.SubsystemExtension;
import org.jboss.ballroom.client.widgets.window.DefaultWindow;
import org.jboss.dmr.client.ModelNode;
import org.jboss.dmr.client.Property;
import org.jboss.dmr.client.dispatch.DispatchAsync;
import org.jboss.dmr.client.dispatch.impl.DMRAction;
import org.jboss.dmr.client.dispatch.impl.DMRResponse;

import com.acme.corp.tracker.client.i18n.I18n;

import org.useware.kernel.gui.behaviour.StatementContext;

import javax.inject.Inject;

import java.util.List;
import java.util.Map;

import static org.jboss.dmr.client.ModelDescriptionConstants.READ_RESOURCE_OPERATION;
import static org.jboss.dmr.client.ModelDescriptionConstants.OP;
import static org.jboss.dmr.client.ModelDescriptionConstants.ADDRESS;
import static org.jboss.dmr.client.ModelDescriptionConstants.RESULT;

public class ExtensionPresenter extends Presenter<ExtensionPresenter.MyView, ExtensionPresenter.MyProxy> {

    public final static String ROOT_RESOURCE = "{selected.profile}/subsystem=tracker";
    public final static AddressTemplate ROOT_RESOURCE_ADDRESS = AddressTemplate.of(ROOT_RESOURCE);


    @ProxyCodeSplit
    @NameToken("tracker")
    @SubsystemExtension(name="Tracker", group = "Tracker", key="tracker")
    @RequiredResources(resources = {"{selected.profile}/subsystem=tracker"})
    public interface MyProxy extends ProxyPlace<ExtensionPresenter> {}


    public interface MyView extends View, HasPresenter<ExtensionPresenter> {

        void updateRootView(ModelNode data);
        void updateTypes(List<Property> items);
    }


    // ------------------------------------------------------ presenter lifecycle

    private final StatementContext statementContext;
    private final DispatchAsync dispatcher;
    private final CrudOperationDelegate operationDelegate;
    private final I18n i18n;
    
    private DefaultWindow window;
    
    private ResourceDescriptionRegistry descriptionRegistry;
    
    CrudOperationDelegate.Callback defaultOpCallbacks = new CrudOperationDelegate.Callback() {
        @Override
        public void onSuccess(AddressTemplate address, String name) {
            Console.info(Console.MESSAGES.successfullyModifiedResource(address.resolve(statementContext, name).toString()));
            loadSubsystem();
        }

        @Override
        public void onFailure(AddressTemplate address, String name, Throwable t) {
            loadSubsystem();
            Console.error(Console.MESSAGES.failedToModifyResource(address.resolve(statementContext, name).toString()), t.getMessage());
        }
    };
    
    @Inject
    public ExtensionPresenter(final EventBus eventBus,
            final MyView view,
            final MyProxy proxy,
            final StatementContext statementContext,
            final DispatchAsync dispatcher,
            final I18n i18n,
            ResourceDescriptionRegistry descriptionRegistry) {
        super(eventBus, view, proxy, MainLayoutPresenter.TYPE_MainContent);
        this.statementContext = statementContext;
        this.dispatcher = dispatcher;
        this.i18n = i18n;
        this.descriptionRegistry = descriptionRegistry;
        this.operationDelegate = new CrudOperationDelegate(statementContext, dispatcher);
    }

    @Override
    protected void onBind() {
        super.onBind();
        getView().setPresenter(this);
    }

    @Override
    protected void onReset() {
        super.onReset();
        loadSubsystem();
    }


    // ------------------------------------------------------ subsystem methods

    private void loadSubsystem() {
        ModelNode operation = new ModelNode();
        operation.get(OP).set(READ_RESOURCE_OPERATION);
        operation.get(ADDRESS).set(Baseadress.get());
        operation.get(ADDRESS).add("subsystem", "tracker");
        operation.get("recursive-depth").set(1);
        dispatcher.execute(new DMRAction(operation), new SimpleCallback<DMRResponse>() {
            @Override
            public void onSuccess(final DMRResponse response) {
                ModelNode body = response.get();
                if (body.isFailure()) {
                    Console.error(i18n.extensionConstants().load_failed(), body.getFailureDescription());
                } else {
                    ModelNode tracker = body.get(RESULT);
                    getView().updateRootView(tracker);
                    getView().updateTypes(tracker.get("type").asPropertyList());//TODO--
                }
            }
        });
    }

    public void onSaveResource(final Map<String, Object> changedValues) {
        operationDelegate.onSaveResource(ROOT_RESOURCE_ADDRESS, null, changedValues,
                new CrudOperationDelegate.Callback() {
                    @Override
                    public void onFailure(final AddressTemplate addressTemplate, final String name, final Throwable t) {
                        Console.error(i18n.consoleMessages().modificationFailed("subsystem 'tracker'"), t.getMessage());
                    }

                    @Override
                    public void onSuccess(final AddressTemplate addressTemplate, final String name) {
                        Console.info(i18n.consoleMessages().modified("subsystem 'tracker'"));
                        loadSubsystem();
                    }
                });
    }

    public void onLaunchAddResourceDialog(AddressTemplate address) {
       
        String type = address.getResourceType();

        window = new DefaultWindow(Console.MESSAGES.createTitle(type.toUpperCase()));
        window.setWidth(640);
        window.setHeight(480);

        window.setWidget(
                new AddResourceDialog(
                        Console.MODULES.getSecurityFramework().getSecurityContext(getProxy().getNameToken()),
                        descriptionRegistry.lookup(address),
                        new AddResourceDialog.Callback() {
                            @Override
                            public void onAdd(ModelNode payload) {
                                window.hide();
                                operationDelegate.onCreateResource(
                                        address, payload.get("name").asString(), payload, defaultOpCallbacks);
                            }

                            @Override
                            public void onCancel() {
                                window.hide();
                            }
                        }
                )
        );

        window.setGlassEnabled(true);
        window.center();
        
    }

    public void onRemoveResource(AddressTemplate address, String name) {
        operationDelegate.onRemoveResource(address, name, defaultOpCallbacks);
    }

    public void onSaveNamedResource(AddressTemplate address, String name, Map changeset) {
        operationDelegate.onSaveResource(address, name, changeset, defaultOpCallbacks);
    }
}
