package org.jboss.as.console.client.acme;

import java.util.Map;

public interface Persistable<T> {
    void save(T t, Map<String, Object> changeset);
}
