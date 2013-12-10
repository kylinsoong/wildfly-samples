* Writing an MBean that relies on a JBoss service requires you to follow the JBoss service pattern. The JBoss MBean service pattern consists of a set of life cycle operations like create, start, stop, and destroy that provide state change notifications.
* We can create a Service MBean in JBoss EAP6 By simply creating a Class and adding start() and stop() to it and by creating a $YOUR_SAR/META-INF/jboss-service.xml like following:

* Step-1). create an interface as following:
~~~
public interface TestMBean
  {
      public void setMessage(String message);
      public String getMessage();
  }
~~~

* Step-2). Create an implementation class of above interface along with start() and stop() additional methods.
~~~
public class Test implements TestMBean
  {
      boolean flag=true;
      public String Message;

      public Test()
      {
         System.out.println(" TestMBean is activated...inside Test() constructor--setting default Message=Hello");;
      }

      public void setMessage(String Message)
      {
          System.out.println("nt Server Watch Message is set to : "+Message);
          this.Message=Message;
      }

      public String getMessage()
      {
          System.out.println(" Server Watch Message is set to : "+Message);
          return this.Message;
      }

     public void start() throws Exception
      {
            System.out.println("Starting start() Test invoked");
            Message="JBoss";
      }

     public void stop() throws Exception
      {
            System.out.println("Stopping stop() Test  invoked");
      }
  }
~~~

* Step3). Compile above classes and place it inside test.sar

* Step4). Add a jboss-service.xml inside test.sar/META-INF as following:
~~~
<?xml version="1.0" encoding="UTF-8"?>
<server xmlns="urn:jboss:service:7.0"
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xsi:schemaLocation="urn:jboss:service:7.0 jboss-service_7_0.xsd">
    <mbean code="org.jboss.jmx.test.Test" name="test.testing:service=MyTestMBean">
         <attribute name="Message">JBoss</attribute>
    </mbean>
</server>
~~~
