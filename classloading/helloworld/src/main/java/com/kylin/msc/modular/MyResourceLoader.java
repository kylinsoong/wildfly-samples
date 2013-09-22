package com.kylin.msc.modular;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import org.jboss.modules.AbstractResourceLoader;
import org.jboss.modules.ClassSpec;
import org.jboss.modules.PackageSpec;
import org.jboss.modules.Resource;

import static com.kylin.msc.modular.Util.getResourceFile;
import static com.kylin.msc.modular.Util.getResourceNameOfClass;
import static com.kylin.msc.modular.Util.readBytes;


public class MyResourceLoader extends AbstractResourceLoader {
	
	private final Map<String, ClassSpec> classSpecs = new HashMap<String, ClassSpec>();
    private final Map<String, Resource> resources = new HashMap<String, Resource>();
    private final Set<String> paths = new HashSet<String>();
    private Manifest manifest;

    public String getRootName() {
        return "test";
    }
    
    public ClassSpec getClassSpec(final String fileName) throws IOException {
        final Map<String, ClassSpec> classSpecs = this.classSpecs;
        return classSpecs.get(fileName);
    }
    
    void addClassSpec(final String name, final ClassSpec classSpec) {
        final Map<String, ClassSpec> classSpecs = this.classSpecs;
        classSpecs.put(name.replace('.', '/') + ".class", classSpec);
        addPaths(getPathFromClassName(name));
    }
    
    public PackageSpec getPackageSpec(final String name) throws IOException {
        return getPackageSpec(name, getManifest(), null);
    }
    
    private Manifest getManifest() throws IOException {
        if(manifest != null)
            return manifest;

        final Resource manifestResource = getResource("META-INF/MANIFEST.MF");
        if(manifestResource  == null)
            return null;
        final InputStream manifestStream = manifestResource.openStream();
        try {
            manifest = new Manifest(manifestStream);
        } finally {
            if(manifestStream != null) manifestStream.close();
        }
        return manifest;
    }
    
    private static String getDefinedAttribute(Attributes.Name name, Attributes entryAttribute, Attributes mainAttribute) {
        final String value = entryAttribute == null ? null : entryAttribute.getValue(name);
        return value == null ? mainAttribute == null ? null : mainAttribute.getValue(name) : value;
    }
    
    public Resource getResource(final String name) {
        String resourceName = name;
        if (resourceName.startsWith("/"))
            resourceName = resourceName.substring(1);
        final Map<String, Resource> resources = this.resources;
        return resources.get(resourceName);
    }

    void addResource(final String name, final Resource resource) {
        final Map<String, Resource> resources = this.resources;
        resources.put(name, resource);
        addPaths(getPathFromResourceName(name));
    }

    private void addPaths(String path) {
        final String[] parts = path.split("/");
        String current = "";
        for(String part : parts) {
            current += part;
            paths.add(current);
            current += "/";
        }
    }
    
    public String getLibrary(String name) {
        return null;
    }
    
    public Collection<String> getPaths() {
        return paths;
    }
    
    private String getPathFromResourceName(final String resourcePath) {
        int idx = resourcePath.lastIndexOf('/');
        final String path = idx > -1 ? resourcePath.substring(0, idx) : "";
        return path;
    }

    private String getPathFromClassName(final String className) {
        int idx = className.lastIndexOf('.');
        return idx > -1 ? className.substring(0, idx).replace('.', '/') : "";
    }

    public static MyResourceLoaderBuilder build() {
        return new MyResourceLoaderBuilder();
    }
    
    public static class MyResourceLoaderBuilder {
        
    	private final MyResourceLoader resourceLoader = new MyResourceLoader();

        public MyResourceLoader create() {
            return resourceLoader;
        }

        public MyResourceLoaderBuilder addResource(final String name, final URL resourceUrl) {
            addResource(name, new Resource() {
                @Override
                public String getName() {
                    return name;
                }

                @Override
                public URL getURL() {
                    return resourceUrl;
                }

                @Override
                public InputStream openStream() throws IOException {
                    return resourceUrl.openStream();
                }

                @Override
                public long getSize() {
                    return 0L;
                }
            });
            return this;
        }

        public MyResourceLoaderBuilder addResource(final String name, final File resource) throws MalformedURLException {
            final URL url = resource.toURI().toURL();
            addResource(name, new Resource() {
                @Override
                public String getName() {
                    return name;
                }

                @Override
                public URL getURL() {
                    return url;
                }

                @Override
                public InputStream openStream() throws IOException {
                    return new FileInputStream(resource);
                }

                @Override
                public long getSize() {
                    return resource.length();
                }
            });
            return this;
        }

        public MyResourceLoaderBuilder addResource(final String name, final Resource resource) {
            final MyResourceLoader resourceLoader = this.resourceLoader;
            resourceLoader.addResource(name, resource);
            return this;
        }

        public MyResourceLoaderBuilder addResources(final File base) throws Exception {
            addResources("", base);
            return this;
        }

        private void addResources(final String pathBase, final File base) throws Exception {
            
        	if(!base.isDirectory()) {
        		throw new Exception(base + " should be Directory");
        	}
        	
            final File[] children = base.listFiles();
            for (File child : children) {
                final String childPath = pathBase + child.getName();
                if (child.isDirectory()) {
                    addResources(childPath + "/", child);
                } else {
                    addResource(childPath, child);
                }
            }
        }

        public MyResourceLoaderBuilder addClass(final Class<?> aClass) throws Exception {
            final ClassSpec classSpec = new ClassSpec();
            classSpec.setCodeSource(aClass.getProtectionDomain().getCodeSource());
            final byte[] classBytes = getClassBytes(aClass);
            classSpec.setBytes(classBytes);
            addClassSpec(aClass.getName(), classSpec);
            return this;
        }
        
        public byte[] getClassBytes(final Class<?> aClass) throws Exception {
            final String resourcePath = getResourceNameOfClass(aClass);
            final File classFile = getResourceFile(aClass, resourcePath);
            byte[] classBytes = readBytes(new FileInputStream(classFile));
            return classBytes;
        }
        
        

        public MyResourceLoaderBuilder addClasses(final Class<?>... classes) throws Exception {
            for(Class<?> aClass : classes) {
                addClass(aClass);
            }
            return this;
        }

        public MyResourceLoaderBuilder addClassSpec(final String name, final ClassSpec classSpec) {
            final MyResourceLoader resourceLoader = this.resourceLoader;
            resourceLoader.addClassSpec(name, classSpec);
            return this;
        }
    }

}
