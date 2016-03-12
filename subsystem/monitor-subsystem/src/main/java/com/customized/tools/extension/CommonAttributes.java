package com.customized.tools.extension;

interface CommonAttributes {
	
	final String NAMESPACE_MONITOR_1_0 = "urn:com.customized.tools.monitor:1.0";
	
//	String FOLDERPATH_MODEL = "folder-path-model";
//	String RESULTFILE_MODEL = "file-name-model";
	
	//path model
	String PATH_MODEL = "path-model";
	String FOLDERPATH = "folder";
	String RESULTFILENAME = "file"; 
	
	//persist model
	String PERSIST_MODEL = "persist-model";
	String PERSISTTOFILE = "persist"; 
	
	//elements 
	String ELEMENT_FOLDER_PATH = "folder-path";
	String ELEMENT_RESULT_FILE_NAME = "result-file";
	String ELEMENT_PERSIST_TO_FILE = "persist-to-file";
	
	//attributes
	String ATTR_FOLDERNAME = "folderName";
	String ATTR_FILENAME = "fileName";
	String ATTR_ISPERSIST = "isPersist";
	
	Boolean BOOLEAN_FALSE = false;
	String DEFAULT_FILENAME="monitor.out";
	String DEFAULT_FOLDERNAME = "jboss.server.base.dir";

}
