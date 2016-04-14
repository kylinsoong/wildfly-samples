package org.jboss.as.console.client.acme;

import org.jboss.as.console.client.acme.model.TrackerSubsystem;
import org.jboss.as.console.client.layout.OneToOneLayout;
import org.jboss.ballroom.client.widgets.forms.CheckBoxItem;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class ConfigurationEditor {
    
    private TrackerPresenter presenter; 
    
    private TrackerModelForm<TrackerSubsystem> commonForm;
    
    public ConfigurationEditor(TrackerPresenter presenter) {
        this.presenter = presenter;
    }
    
    public void setConfigurationBean(TrackerSubsystem bean) {
        this.commonForm.edit(bean);
    }
    
    public Widget asWidget() {
        
        // common
        CheckBoxItem allowEnvFunction = new CheckBoxItem("showCoolDeployments", "Show Cool Deployments");
        
        this.commonForm = new TrackerModelForm<TrackerSubsystem>(TrackerSubsystem.class, this.presenter, allowEnvFunction);
        
        HTML title = new HTML();
        title.setStyleName("content-header-label");
        title.setText("Acme Tracker");
        
        OneToOneLayout layoutBuilder = new OneToOneLayout()
                .setPlain(true)
                .setTitle("Tracking Deployments")
                .setHeadlineWidget(title)
                .setDescription("Tracking Deployments")
                .addDetail("Common", this.commonForm.asWidget());
        
        return layoutBuilder.build();
    }
    
    

}
