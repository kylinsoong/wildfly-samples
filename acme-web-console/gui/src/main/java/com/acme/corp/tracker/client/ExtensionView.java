package com.acme.corp.tracker.client;

import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.Widget;
import org.jboss.as.console.client.core.SuspendableViewImpl;
import org.jboss.as.console.client.layout.SimpleLayout;
import org.jboss.as.console.client.rbac.SecurityFramework;
import org.jboss.as.console.client.v3.ResourceDescriptionRegistry;
import org.jboss.as.console.client.v3.dmr.ResourceDescription;
import org.jboss.as.console.mbui.widgets.ModelNodeForm;
import org.jboss.as.console.mbui.widgets.ModelNodeFormBuilder;
import org.jboss.ballroom.client.rbac.SecurityContext;
import org.jboss.ballroom.client.widgets.forms.FormCallback;
import org.jboss.dmr.client.ModelNode;

import javax.inject.Inject;
import java.util.Map;

import static org.jboss.dmr.client.ModelDescriptionConstants.DESCRIPTION;

public class ExtensionView extends SuspendableViewImpl implements ExtensionPresenter.MyView {

    private final ResourceDescriptionRegistry descriptionRegistry;
    private final SecurityFramework securityFramework;

    private ExtensionPresenter presenter;
    private ModelNodeForm form;

    @Inject
    public ExtensionView(final ResourceDescriptionRegistry descriptionRegistry, final SecurityFramework securityFramework) {
        this.securityFramework = securityFramework;
        this.descriptionRegistry = descriptionRegistry;
    }

    @Override
    public Widget createWidget() {
        SecurityContext securityContext = securityFramework.getSecurityContext(presenter.getProxy().getNameToken());
        ResourceDescription resourceDescription = descriptionRegistry.lookup(
                ExtensionPresenter.ROOT_RESOURCE_ADDRESS);

        ModelNodeFormBuilder.FormAssets formAssets = new ModelNodeFormBuilder()
                .setConfigOnly()
                .setResourceDescription(resourceDescription)
                .setSecurityContext(securityContext)
                .build();

        form = formAssets.getForm();
        form.setToolsCallback(new FormCallback() {
            @Override
            @SuppressWarnings("unchecked")
            public void onSave(Map changedValues) {
                presenter.onSaveResource(changedValues);
            }

            @Override
            public void onCancel(Object entity) {
                form.cancel();
            }
        });

        return new SimpleLayout()
                .setPlain(true)
                .setHeadline("Tracker")
                .setDescription(SafeHtmlUtils.fromString(resourceDescription.get(DESCRIPTION).asString()))
                .addContent("Attributes", formAssets.asWidget())
                .build();
    }

    @Override
    public void setPresenter(final ExtensionPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void update(final ModelNode data) {
        form.edit(data);
    }
}
