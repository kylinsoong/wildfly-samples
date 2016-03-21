package org.jboss.as.console.client.acme.model;

import org.jboss.as.console.client.widgets.forms.Address;
import org.jboss.as.console.client.widgets.forms.Binding;

@Address("/subsystem=tracker")
public interface SubsystemConfiguration {

    @Binding(detypedName= "show-cool-deployments")
    public boolean isShowCoolDeployments();
    public void setShowCoolDeployments(boolean isShowCool);

}
