/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2010, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package com.acme.corp.tracker.client.i18n;

import org.jboss.as.console.client.core.UIConstants;
import org.jboss.as.console.client.core.UIMessages;

import javax.inject.Inject;

public class I18n {

    private final UIConstants consoleConstants;
    private final UIMessages consoleMessages;
    private final ExtensionConstants extensionConstants;
    private final ExtensionMessages extensionMessages;

    @Inject
    public I18n(final UIConstants consoleConstants,
            final UIMessages consoleMessages,
            final ExtensionConstants extensionConstants,
            final ExtensionMessages extensionMessages) {
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
