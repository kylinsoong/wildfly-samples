
package org.jboss.as.console.client.acme.i18n;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;

public class I18nModule extends AbstractGinModule {

    @Override
    protected void configure() {
        bind(I18n.class).in(Singleton.class);
    }
}
