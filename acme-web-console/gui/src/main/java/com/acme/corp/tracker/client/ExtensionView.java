package com.acme.corp.tracker.client;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.Widget;

import org.jboss.as.console.client.core.SuspendableViewImpl;
import org.jboss.as.console.client.rbac.SecurityFramework;
import org.jboss.as.console.client.v3.ResourceDescriptionRegistry;
import org.jboss.as.console.client.widgets.tabs.DefaultTabLayoutPanel;
import org.jboss.dmr.client.ModelNode;
import org.jboss.dmr.client.Property;

import javax.inject.Inject;

import java.util.List;

public class ExtensionView extends SuspendableViewImpl implements ExtensionPresenter.MyView {

    private final ResourceDescriptionRegistry descriptionRegistry;
    private final SecurityFramework securityFramework;

    private ExtensionPresenter presenter;
    
    private RootTrackerView rootView;
    private TypeView typeView;

    @Inject
    public ExtensionView(final ResourceDescriptionRegistry descriptionRegistry, final SecurityFramework securityFramework) {
        this.securityFramework = securityFramework;
        this.descriptionRegistry = descriptionRegistry;
    }

    @Override
    public Widget createWidget() {
       
        rootView = new RootTrackerView(presenter, securityFramework, descriptionRegistry);
        typeView = new TypeView(presenter, securityFramework, descriptionRegistry);
        
        DefaultTabLayoutPanel tabLayoutpanel = new DefaultTabLayoutPanel(40, Style.Unit.PX);
        tabLayoutpanel.addStyleName("default-tabpanel");
        
        tabLayoutpanel.add(rootView.asWidget(), "Tracker", true);
        tabLayoutpanel.add(typeView.asWidget(), "Types", true);
        
        tabLayoutpanel.selectTab(0);
        
        return tabLayoutpanel;
    }

    @Override
    public void setPresenter(final ExtensionPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void updateRootView(final ModelNode data) {
        rootView.setData(data);
    }

    @Override
    public void updateTypes(List<Property> items) {
        typeView.setData(items);
    }
}
