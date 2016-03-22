package org.jboss.as.console.client.acme.model;

import org.jboss.as.console.spi.BeanFactoryExtension;

import com.google.web.bindery.autobean.shared.AutoBean;

@BeanFactoryExtension
public interface DataModelFactory {
    AutoBean<SubsystemConfiguration> getSubSystemModel();
}
