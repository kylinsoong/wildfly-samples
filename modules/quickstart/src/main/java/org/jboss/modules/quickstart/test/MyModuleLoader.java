package org.jboss.modules.quickstart.test;

import java.util.HashMap;
import java.util.Map;

import org.jboss.modules.ModuleIdentifier;
import org.jboss.modules.ModuleLoadException;
import org.jboss.modules.ModuleLoader;
import org.jboss.modules.ModuleSpec;

public class MyModuleLoader extends ModuleLoader {
	
	private final Map<ModuleIdentifier, ModuleSpec> moduleSpecs = new HashMap<ModuleIdentifier, ModuleSpec>();

	public String toString() {
		return "test@" + System.identityHashCode(this);
	}

	protected ModuleSpec findModule(ModuleIdentifier moduleIdentifier) throws ModuleLoadException {
		
		final ModuleSpec moduleSpec = moduleSpecs.get(moduleIdentifier);
		
		if(moduleSpec == null) {
			throw new ModuleLoadException("No module spec found for module " + moduleIdentifier);
		}
		
		return moduleSpec;
	}
	
	public void addModuleSpec(final ModuleSpec moduleSpec) {
        moduleSpecs.put(moduleSpec.getModuleIdentifier(), moduleSpec);
    }
	
	public static void main(String[] args) {
		System.out.println(new MyModuleLoader());
	}

}
