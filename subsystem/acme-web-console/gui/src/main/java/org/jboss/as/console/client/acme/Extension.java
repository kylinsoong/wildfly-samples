package org.jboss.as.console.client.acme;

import org.jboss.as.console.spi.GinExtension;

import com.google.gwt.inject.client.AsyncProvider;


@GinExtension
public interface Extension {
    AsyncProvider<SubsystemPresenter> getSubsystemPresenter();
}
