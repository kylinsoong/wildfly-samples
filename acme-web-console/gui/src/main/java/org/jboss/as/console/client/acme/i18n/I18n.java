
package org.jboss.as.console.client.acme.i18n;

import org.jboss.as.console.client.core.UIConstants;
import org.jboss.as.console.client.core.UIMessages;

import javax.inject.Inject;

public class I18n {

    private final UIConstants consoleConstants;
    private final UIMessages consoleMessages;
    private final ExtensionConstants extensionConstants;
    private final ExtensionMessages extensionMessages;

    @Inject
    public I18n(final UIConstants consoleConstants, final UIMessages consoleMessages, final ExtensionConstants extensionConstants, final ExtensionMessages extensionMessages) {
        this.consoleConstants = consoleConstants;
        this.consoleMessages = consoleMessages;
        this.extensionConstants = extensionConstants;
        this.extensionMessages = extensionMessages;
    }

    public UIConstants consoleConstants() {
        return consoleConstants;
    }

    public UIMessages consoleMessages() {
        return consoleMessages;
    }

    public ExtensionConstants extensionConstants() {
        return extensionConstants;
    }

    public ExtensionMessages extensionMessages() {
        return extensionMessages;
    }
}
