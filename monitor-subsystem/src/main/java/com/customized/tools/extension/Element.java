package com.customized.tools.extension;

import java.util.HashMap;
import java.util.Map;

public enum Element {
	
	UNKNOWN(null),
	FOLEDER_PATH("folder-path"),
	RESULT_FILE_NAME("result-file"),
	PERSIST_TO_FILE("persist-to-file"),
	;
	
	private final String name;

    Element(final String name) {
        this.name = name;
    }
    
    public String getLocalName() {
        return name;
    }
    
    private static final Map<String, Element> MAP;
    
    static {
        final Map<String, Element> map = new HashMap<String, Element>();
        for (Element element : values()) {
            final String name = element.getLocalName();
            if (name != null) map.put(name, element);
        }
        MAP = map;
    }
    
    public static Element forName(String localName) {
        final Element element = MAP.get(localName);
        return element == null ? UNKNOWN : element;
    }

}
