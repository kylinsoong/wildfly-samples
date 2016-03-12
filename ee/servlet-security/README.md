## Servlet Security

This example show how to set the Servlet Security.

## TEST

### Step.1 

Start WildFly, create sercurity domain `test-security`:

~~~
$ ./bin/standalone.sh
$ ./bin/jboss-cli.sh --connect --file=test-security-create.cli
~~~

> NOTE: 'test-security-remove.cli' can be used to remove `test-security`.

### Step.2

Edit 'webapp/WEB-INF/jboss-web.xml', add the following content:

~~~
<?xml version="1.0" encoding="UTF-8"?>
<jboss-web>
    <security-domain>java:/jaas/test-security</security-domain>
</jboss-web>
~~~

### Step.3

Edit 'webapp/WEB-INF/web.xml', add the following content:

~~~
<security-role>
        <description>security role</description>
        <role-name>test</role-name>
    </security-role>

    <security-constraint>
        <display-name>require valid user</display-name>
        <web-resource-collection>
            <web-resource-name>Test Application</web-resource-name>
            <url-pattern>/*</url-pattern>
        </web-resource-collection>
        <auth-constraint>
            <role-name>test</role-name>
        </auth-constraint>
    </security-constraint>

   <!-- Configure login to be HTTP Basic -->
   <login-config>
      <auth-method>BASIC</auth-method>
      <realm-name>servlet-security/</realm-name>
   </login-config>
~~~

### Step.4

Deploy `wildfly-servlet-security.war` to WildFly, access http://localhost:8080/servlet-security/

> NOTE: If 'This is index page.' message appeared on the page means access correct.
