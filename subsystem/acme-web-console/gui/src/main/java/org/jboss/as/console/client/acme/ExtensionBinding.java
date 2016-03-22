package org.jboss.as.console.client.acme;

import org.jboss.as.console.spi.GinExtensionBinding;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;


@GinExtensionBinding
public class ExtensionBinding extends AbstractPresenterModule {

    @Override
    protected void configure() {
        bindPresenter(SubsystemPresenter.class,
        		SubsystemPresenter.TrackerView.class,
                SubsystemView.class,
                SubsystemPresenter.TrackerProxy.class);
    }
}
