1. How to build?
  mvn clean dependency:copy-dependencies install 
  ant

2. How to run?
  java -jar jboss-modules-1.1.2.GA.jar -mp "/home/kylin/work/project/JVM/msc/helloworld/build/helloworld/modules" com.kylin.msc.boot

  1) Modular Class Loading Test
	java -jar jboss-modules-1.1.2.GA.jar -mp modules test.test
  2) Print Hierarchy Class Loader
	java -jar jboss-modules-1.1.2.GA.jar -mp modules test.print
  3) org.w3c.dom ClassNotFound Test
        java -jar jboss-modules-1.1.2.GA.jar -mp modules test.w3c
  
