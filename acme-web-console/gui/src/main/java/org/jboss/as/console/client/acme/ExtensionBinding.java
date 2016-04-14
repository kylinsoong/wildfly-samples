package org.jboss.as.console.client.acme;

import org.jboss.as.console.spi.GinExtensionBinding;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;


@GinExtensionBinding
public class ExtensionBinding extends AbstractPresenterModule {

    @Override
    protected void configure() {
        bindPresenter(TrackerPresenter.class,
        		TrackerPresenter.MyView.class,
                TrackerSubsystemView.class,
                TrackerPresenter.MyProxy.class);
    }
}
