package org.jboss.as.console.client.acme;

import org.jboss.as.console.client.core.SuspendableViewImpl;
import org.jboss.as.console.client.rbac.SecurityFramework;
import org.jboss.as.console.client.v3.ResourceDescriptionRegistry;
import org.jboss.as.console.client.widgets.pages.PagedView;
import org.jboss.as.console.client.widgets.tabs.DefaultTabLayoutPanel;
import org.jboss.as.console.client.acme.ConfigurationEditor;
import org.jboss.as.console.client.acme.model.SubsystemConfiguration;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class SubsystemView extends SuspendableViewImpl implements SubsystemPresenter.TrackerView {
    
    private final ResourceDescriptionRegistry descriptionRegistry;
    private final SecurityFramework securityFramework;
    
    private ConfigurationEditor configurationEditor;
    
    private SubsystemPresenter presenter;
    
    @Inject
    public SubsystemView(ResourceDescriptionRegistry descriptionRegistry, SecurityFramework securityFramework) {
        this.descriptionRegistry = descriptionRegistry;
        this.securityFramework = securityFramework;
    }
    
    @Override
    public void setPresenter(SubsystemPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setConfigurationBean(SubsystemConfiguration bean) {
        this.configurationEditor.setConfigurationBean(bean);
    }

    @Override
    public Widget createWidget() {
        
        DefaultTabLayoutPanel layout  = new DefaultTabLayoutPanel(40, Style.Unit.PX);
        layout.addStyleName("default-tabpanel");
        PagedView panel = new PagedView(true);

        this.configurationEditor = new ConfigurationEditor(this.presenter);
        
        panel.addPage("Tracker Deployments", this.configurationEditor.asWidget());
        
        panel.showPage(0);

        Widget panelWidget = panel.asWidget();
        layout.add(panelWidget, "Tracker");

        return layout;
    }

}
