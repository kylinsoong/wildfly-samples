package com.kylin.msc.modular;

import java.io.File;

import org.jboss.modules.DependencySpec;
import org.jboss.modules.Module;
import org.jboss.modules.ModuleClassLoader;
import org.jboss.modules.ModuleIdentifier;
import org.jboss.modules.ModuleSpec;
import org.jboss.modules.ResourceLoaderSpec;
import org.jboss.modules.filter.MultiplePathFilterBuilder;
import org.jboss.modules.filter.PathFilters;

import com.kylin.msc.modular.test.ImportedClass;
import com.kylin.msc.modular.test.ImportedInterface;
import com.kylin.msc.modular.test.TestClass;


public class ClassLoaderTest extends TestBase {
	
	private static final ModuleIdentifier MODULE_WITH_CONTENT_ID = ModuleIdentifier.fromString("test-with-content");
	private static final ModuleIdentifier MODULE_WITH_RESOURCE_ID = ModuleIdentifier.fromString("test-with-resource");
	private static final ModuleIdentifier MODULE_TO_IMPORT_ID = ModuleIdentifier.fromString("test-to-import");
    private static final ModuleIdentifier MODULE_WITH_EXPORT_ID = ModuleIdentifier.fromString("test-with-export");
    private static final ModuleIdentifier MODULE_WITH_DOUBLE_EXPORT_ID = ModuleIdentifier.fromString("test-with-double-export");
    private static final ModuleIdentifier MODULE_WITH_INVERTED_DOUBLE_EXPORT_ID = ModuleIdentifier.fromString("test-with-inverted-double-export");
    private static final ModuleIdentifier MODULE_WITH_FILTERED_EXPORT_ID = ModuleIdentifier.fromString("test-with-filtered-export");
    private static final ModuleIdentifier MODULE_WITH_FILTERED_IMPORT_ID = ModuleIdentifier.fromString("test-with-filtered-import");
    private static final ModuleIdentifier MODULE_WITH_FILTERED_DOUBLE_EXPORT_ID = ModuleIdentifier.fromString("test-with-filtered-double-export");
	
	private MyModuleLoader myModuleLoader ;
	
	public ClassLoaderTest() throws Exception {
		
		myModuleLoader = new MyModuleLoader();
		
		final ModuleSpec.Builder moduleWithContentBuilder = ModuleSpec.build(MODULE_WITH_CONTENT_ID);
		moduleWithContentBuilder.addResourceRoot(ResourceLoaderSpec
				.createResourceLoaderSpec(MyResourceLoader
						.build()
						.addClass(TestClass.class)
						.addResources(new File(RUNNABLE_MODULE_PATH + File.separator + "modulecontentloader/rootOne"))
						.create()));
		
		moduleWithContentBuilder.addDependency(DependencySpec.createModuleDependencySpec(MODULE_TO_IMPORT_ID));
		moduleWithContentBuilder.addDependency(DependencySpec.createLocalDependencySpec());
		myModuleLoader.addModuleSpec(moduleWithContentBuilder.create());
		
		final ModuleSpec.Builder moduleWithResourceBuilder = ModuleSpec.build(MODULE_WITH_RESOURCE_ID);
		moduleWithResourceBuilder.addResourceRoot(ResourceLoaderSpec
				.createResourceLoaderSpec(MyResourceLoader
						.build()
						.addClasses(TestClass.class)
						.addResources(new File(RUNNABLE_MODULE_PATH)).create()));
		moduleWithResourceBuilder.addDependency(DependencySpec.createModuleDependencySpec(MODULE_TO_IMPORT_ID));
        moduleWithResourceBuilder.addDependency(DependencySpec.createLocalDependencySpec());
        myModuleLoader.addModuleSpec(moduleWithResourceBuilder.create());
        
        final ModuleSpec.Builder moduleToImportBuilder = ModuleSpec.build(MODULE_TO_IMPORT_ID);
        moduleToImportBuilder.addResourceRoot(ResourceLoaderSpec.createResourceLoaderSpec(
        		MyResourceLoader.build()
                .addClass(ImportedClass.class)
                .addClass(ImportedInterface.class)
                .addResources(new File(RUNNABLE_MODULE_PATH + File.separator +"modulecontentloader/rootTwo"))
                .create()
        ));
        moduleToImportBuilder.addDependency(DependencySpec.createLocalDependencySpec());
        myModuleLoader.addModuleSpec(moduleToImportBuilder.create());
        
        final ModuleSpec.Builder moduleWithExportBuilder = ModuleSpec.build(MODULE_WITH_EXPORT_ID);
        moduleWithExportBuilder.addDependency(DependencySpec.createModuleDependencySpec(MODULE_TO_IMPORT_ID, true, false));
        moduleWithExportBuilder.addDependency(DependencySpec.createLocalDependencySpec());
        myModuleLoader.addModuleSpec(moduleWithExportBuilder.create());
        
        final MultiplePathFilterBuilder nestedAndOrgJBossExcludingBuilder = PathFilters.multiplePathFilterBuilder(true);
        nestedAndOrgJBossExcludingBuilder.addFilter(PathFilters.match("org/jboss/**"), false);
        nestedAndOrgJBossExcludingBuilder.addFilter(PathFilters.match("nested"), false);
        
        final ModuleSpec.Builder moduleWithExportFilterBuilder = ModuleSpec.build(MODULE_WITH_FILTERED_EXPORT_ID);
        moduleWithExportFilterBuilder.addDependency(DependencySpec.createModuleDependencySpec(nestedAndOrgJBossExcludingBuilder.create(), null, MODULE_TO_IMPORT_ID, false));
        moduleWithExportFilterBuilder.addDependency(DependencySpec.createLocalDependencySpec());
        myModuleLoader.addModuleSpec(moduleWithExportFilterBuilder.create());
        
        final ModuleSpec.Builder moduleWithImportFilterBuilder = ModuleSpec.build(MODULE_WITH_FILTERED_IMPORT_ID);
        moduleWithImportFilterBuilder.addDependency(DependencySpec.createModuleDependencySpec(nestedAndOrgJBossExcludingBuilder.create(), PathFilters.rejectAll(), null, MODULE_TO_IMPORT_ID, false));
        moduleWithImportFilterBuilder.addDependency(DependencySpec.createLocalDependencySpec());
        myModuleLoader.addModuleSpec(moduleWithImportFilterBuilder.create());

        final ModuleSpec.Builder moduleWithDoubleExportBuilder = ModuleSpec.build(MODULE_WITH_DOUBLE_EXPORT_ID);
        moduleWithDoubleExportBuilder.addDependency(DependencySpec.createModuleDependencySpec(MODULE_TO_IMPORT_ID, true));
        moduleWithDoubleExportBuilder.addDependency(DependencySpec.createModuleDependencySpec(MODULE_WITH_CONTENT_ID, true));
        moduleWithDoubleExportBuilder.addDependency(DependencySpec.createLocalDependencySpec());
        myModuleLoader.addModuleSpec(moduleWithDoubleExportBuilder.create());

        final ModuleSpec.Builder moduleWithInvertedDoubleExportBuilder = ModuleSpec.build(MODULE_WITH_INVERTED_DOUBLE_EXPORT_ID);
        moduleWithInvertedDoubleExportBuilder.addDependency(DependencySpec.createModuleDependencySpec(MODULE_WITH_CONTENT_ID, true));
        moduleWithInvertedDoubleExportBuilder.addDependency(DependencySpec.createModuleDependencySpec(MODULE_TO_IMPORT_ID, true));
        moduleWithInvertedDoubleExportBuilder.addDependency(DependencySpec.createLocalDependencySpec());
        myModuleLoader.addModuleSpec(moduleWithInvertedDoubleExportBuilder.create());

        final ModuleSpec.Builder moduleWithFilteredDoubleExportBuilder = ModuleSpec.build(MODULE_WITH_FILTERED_DOUBLE_EXPORT_ID);
        moduleWithFilteredDoubleExportBuilder.addDependency(DependencySpec.createModuleDependencySpec(PathFilters.not(PathFilters.match("nested")), PathFilters.acceptAll(), null, MODULE_TO_IMPORT_ID, false));
        moduleWithFilteredDoubleExportBuilder.addDependency(DependencySpec.createModuleDependencySpec(MODULE_WITH_EXPORT_ID, true));
        moduleWithFilteredDoubleExportBuilder.addDependency(DependencySpec.createLocalDependencySpec());
        myModuleLoader.addModuleSpec(moduleWithFilteredDoubleExportBuilder.create());

	}

	protected void test() throws Exception {
		
		testLocalClassLoad();
		
		testResourceLoad();
		
		testLocalClassLoadNotFound();
		
		testImportClassLoad();
		
		testFilteredImportClassLoad();
	}

	private void testFilteredImportClassLoad() throws Exception {

		pauseln("testFilteredImportClassLoad");
		
		final Module testModule = myModuleLoader.loadModule(MODULE_WITH_FILTERED_IMPORT_ID);
        final ModuleClassLoader classLoader = testModule.getClassLoader();
        
        try {
        	Class<?> testClass = classLoader.loadClass("com.kylin.msc.modular.test.ImportedClass");
        	println(testClass);
		} catch (Exception e) {
			println(e);
		}
	}

	private void testImportClassLoad() throws Exception {

		pauseln("testImportClassLoad");
		
		final Module testModule = myModuleLoader.loadModule(MODULE_WITH_CONTENT_ID);
        final ModuleClassLoader classLoader = testModule.getClassLoader();
        
        try {
        	Class<?> testClass = classLoader.loadClass("com.kylin.msc.modular.test.ImportedClass");
        	println(testClass);
		} catch (Exception e) {
			println(e);
		}
	}

	private void testLocalClassLoadNotFound() throws Exception {
		
		pauseln("testLocalClassLoadNotFound");

		final Module testModule = myModuleLoader.loadModule(MODULE_WITH_CONTENT_ID);
        final ModuleClassLoader classLoader = testModule.getClassLoader();
        
        try {
			classLoader.loadClass("com.kylin.msc.modular.test.NoExistClass");
		} catch (Exception e) {
			println(e);
		}
	}

	private void testResourceLoad() throws Exception {

		pauseln("testLocalClassLoad");

		final Module testModule = myModuleLoader.loadModule(MODULE_WITH_RESOURCE_ID);
        final ModuleClassLoader classLoader = testModule.getClassLoader();
        
        Class<?> testClass = classLoader.loadClass("com.kylin.msc.modular.test.TestClass");
        
        println(testClass.getResource("/file1.txt"));
        println(testClass.getResource("file2.txt"));
        println(testClass.getResource("../../../../../file1.txt"));
        println(testClass.getResource("modules/../file2.txt"));
	}

	private void testLocalClassLoad() throws Exception {
		
		pauseln("testLocalClassLoad");

		final Module testModule = myModuleLoader.loadModule(MODULE_WITH_CONTENT_ID);
        final ModuleClassLoader classLoader = testModule.getClassLoader();
        
        Class<?> testClass = classLoader.loadClass("com.kylin.msc.modular.test.TestClass");
        
        testClass.getMethod("sayHello", new Class[]{String.class}).invoke(testClass.newInstance(), new Object[]{"Kylin Soong"});
	}

	public static void main(String[] args) throws Exception {

		ClassLoaderTest test = new ClassLoaderTest();
		test.test();
	}

}
