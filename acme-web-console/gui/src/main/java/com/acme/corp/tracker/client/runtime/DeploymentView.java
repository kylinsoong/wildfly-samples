package com.acme.corp.tracker.client.runtime;

import java.util.List;

import org.jboss.as.console.client.core.SuspendableViewImpl;
import org.jboss.as.console.client.widgets.tabs.DefaultTabLayoutPanel;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.Widget;

public class DeploymentView extends SuspendableViewImpl implements DeploymentPresenter.MyView {
    
    private DeploymentPresenter presenter;
    private Deployments deployments;
    private Deployments coolDeployments;
    private DefaultTabLayoutPanel tabLayoutpanel;

    @Override
    public Widget createWidget() {
        
        this.deployments = new Deployments(presenter, false);
        this.coolDeployments = new Deployments(presenter, true);
        
        this.tabLayoutpanel = new DefaultTabLayoutPanel(40, Style.Unit.PX);
        tabLayoutpanel.addStyleName("default-tabpanel");
        
        tabLayoutpanel.add(deployments.asWidget(), "Deployments", true);
        tabLayoutpanel.add(coolDeployments.asWidget(), "Cool Deployments", true);
        
        tabLayoutpanel.selectTab(0);
        
        return tabLayoutpanel;
    }

    @Override
    public void setPresenter(DeploymentPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setDeployments(List<String> items, boolean isCool) {

        if(isCool) {
            coolDeployments.setDeployments(items);
        } else {
            deployments.setDeployments(items);
        }
    }

}
