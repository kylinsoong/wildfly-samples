REFERENCE LINK 
--------------

[WFLY-2920](https://issues.jboss.org/browse/WFLY-2920)

[Bugzilla 1064644](https://bugzilla.redhat.com/show_bug.cgi?id=1064644)


REPRODUING STEPS
----------------

* Using the following mvn commands

~~~
mvn clean install
~~~

build and generate 2 EJB jar archives: ejbiiop01/target/ejbiiop01.jar and ejbiiop01/target/ejbiiop01.jar

* Enable a remote debug by commenting out the following line in bin/standalone.conf

~~~
JAVA_OPTS="$JAVA_OPTS -Xrunjdwp:transport=dt_socket,address=8787,server=y,suspend=y"
~~~

Start JBoss standalone-full instance and deploy the ejbiiop01.jar and ejbiiop02.jar

~~~
./standalone.sh -c standalone-full.xml
~~~

* Import Source code to Eclipse set a breakpoint at the following line.

jacorb/src/main/java/org/jboss/as/jacorb/rmi/Util.java:104
~~~
    102         // remote interface?
    103         if (cls.isInterface() && java.rmi.Remote.class.isAssignableFrom(cls)) {
    104             InterfaceAnalysis ia = InterfaceAnalysis.getInterfaceAnalysis(cls);
    105 
    106             return ia.getIDLModuleName() + "::" + ia.getIDLName();
    107         }
~~~

Deployment of those EJBs runs in parallel and hit the breakpoint in 2 threads.  The target class is "javax.ejb.EJBObject".  Set a new breakpoint at the following line.

jacorb/src/main/java/org/jboss/as/jacorb/rmi/WorkCacheManager.java:105
~~~
     83     ContainerAnalysis getAnalysis(final Class cls) throws RMIIIOPViolationException {
    ...
    104         // Do the work
    105         doTheWork(cls, ret);
    106 
~~~

Then disable the old breakpoint and resume those 2 threads.

Let call those threads A and B.  Resume A and B alternately.  This process continues forever and a stack accumulates in both threads.
