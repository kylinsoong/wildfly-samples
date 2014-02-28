What's this
-----------

Primary purpose of this document is supply a reproduction steps for [Bug 1056344](https://bugzilla.redhat.com/show_bug.cgi?id=1056344)


Steps
-----

### create 2 net interface

* Create 2 net interface on Linux server, I used RHEL 6.4 the `uname -a` output as below
~~~
Linux unused 2.6.32-358.el6.x86_64 #1 SMP Tue Jan 29 11:47:41 EST 2013 x86_64 x86_64 x86_64 GNU/Linux
~~~

* Create `ifcfg-eth0:0` file under `/etc/sysconfig/network-scripts` folder, add a static IP addr, my reproduction configuration like:
~~~
DEVICE="eth0:0"
BOOTPROTO=static
NM_CONTROLLED=no
NETMASK=255.255.254.0
TYPE=Ethernet
IPADDR=10.66.192.48
GATEWAY=10.66.193.254
ONBOOT=yes
~~~

* start `eth0:0` via commands:
~~~
ifup eth0:0
~~~

* make sure 2 net interfaces be created correct, use `ifconfig` to test, the `ifconfig` output in my reproduction look likes as below:
~~~
eth0       Link encap:Ethernet  HWaddr 00:21:CC:71:3B:09  
          inet addr:10.66.192.93  Bcast:10.66.193.255  Mask:255.255.254.0
          inet6 addr: fe80::221:ccff:fe71:3b09/64 Scope:Link
          UP BROADCAST RUNNING MULTICAST  MTU:1500  Metric:1
          RX packets:230522 errors:0 dropped:0 overruns:0 frame:0
          TX packets:237609 errors:0 dropped:0 overruns:0 carrier:0
          collisions:0 txqueuelen:1000 
          RX bytes:158896236 (151.5 MiB)  TX bytes:40746633 (38.8 MiB)
          Interrupt:20 Memory:f2500000-f2520000 

eth0:0     Link encap:Ethernet  HWaddr 00:21:CC:71:3B:09  
          inet addr:10.66.192.48  Bcast:10.66.193.255  Mask:255.255.254.0
          UP BROADCAST RUNNING MULTICAST  MTU:1500  Metric:1
          Interrupt:20 Memory:f2500000-f2520000 
~~~

### Test and reproduce the issue via EJB

* install JBoss
~~~
unzip jboss-eap-6.1.0.zip
~~~

* use `add-user.sh` create a Application user for runing EJB client, the create script like below:
~~~
[kylin@unused jboss-eap-6.1]$ cd bin/
[kylin@unused bin]$ ./add-user.sh 

What type of user do you wish to add? 
 a) Management User (mgmt-users.properties) 
 b) Application User (application-users.properties)
(a): b

Enter the details of the new user to add.
Realm (ApplicationRealm) : 
Username : ejb
Password : 
Re-enter Password : 
What roles do you want this user to belong to? (Please enter a comma separated list, or leave blank for none)[  ]: 
About to add user 'ejb' for realm 'ApplicationRealm'
Is this correct yes/no? yes
Added user 'ejb' to file '/home/kylin/jboss-eap-6.1/standalone/configuration/application-users.properties'
Added user 'ejb' to file '/home/kylin/jboss-eap-6.1/domain/configuration/application-users.properties'
Added user 'ejb' with roles  to file '/home/kylin/jboss-eap-6.1/standalone/configuration/application-roles.properties'
Added user 'ejb' with roles  to file '/home/kylin/jboss-eap-6.1/domain/configuration/application-roles.properties'
Is this new user going to be used for one AS process to connect to another AS process? 
e.g. for a slave host controller connecting to the master or for a Remoting connection for server to server EJB calls.
yes/no? yes
To represent the user add the following to the server-identities definition <secret value="cGFzc3dvcmQxIQ==" />
~~~

* start JBoss bind on net interface eth0
~~~
./standalone.sh -bmanagement=10.66.192.93 -b 10.66.192.93
~~~

* deploy service/target/ejb-invocation-service.jar to JBoss
~~~
cp ejb-invocation-service.jar /home/kylin/jboss-eap-6.1/standalone/deployments
~~~

NOTE: the test project under [github link](https://github.com/kylinsoong/wildfly-architecture/tree/master/bug-1056344), donwload the code build can generate jar

* Edit `jboss-ejb-client.properties` like below:
~~~
endpoint.name=client-endpoint
remote.connectionprovider.create.options.org.xnio.Options.SSL_ENABLED=false

remote.connections=default

remote.connection.default.host=10.66.192.93
remote.connection.default.port = 4447
remote.connection.default.connect.options.org.xnio.Options.SASL_POLICY_NOANONYMOUS=false
remote.connection.default.username=ejb
remote.connection.default.password=password1!
~~~

* Run EJB client as below:
~~~
java -cp ejb-invocation-client-remote-1.0.jar:ejb-invocation-service-client.jar:jboss-client.jar org.jboss.ejb.client.GreeterClient
~~~

The EJB client GreeterClient will execute failed with the following trace in client
~~~
Feb 28, 2014 4:50:03 PM org.jboss.ejb.client.EJBClient <clinit>
INFO: JBoss EJB Client version 1.0.21.Final-redhat-1
Feb 28, 2014 4:50:03 PM org.xnio.Xnio <clinit>
INFO: XNIO Version 3.0.7.GA-redhat-1
Feb 28, 2014 4:50:03 PM org.xnio.nio.NioXnio <clinit>
INFO: XNIO NIO Implementation Version 3.0.7.GA-redhat-1
Feb 28, 2014 4:50:03 PM org.jboss.remoting3.EndpointImpl <clinit>
INFO: JBoss Remoting version 3.2.16.GA-redhat-1
Feb 28, 2014 4:50:08 PM org.jboss.ejb.client.remoting.ConfigBasedEJBClientContextSelector setupEJBReceivers
WARN: Could not register a EJB receiver for connection to 10.66.192.93:4447
java.lang.RuntimeException: Operation failed with status WAITING
	at org.jboss.ejb.client.remoting.IoFutureHelper.get(IoFutureHelper.java:93)
	at org.jboss.ejb.client.remoting.ConnectionPool.getConnection(ConnectionPool.java:75)
	at org.jboss.ejb.client.remoting.RemotingConnectionManager.getConnection(RemotingConnectionManager.java:51)
	at org.jboss.ejb.client.remoting.ConfigBasedEJBClientContextSelector.setupEJBReceivers(ConfigBasedEJBClientContextSelector.java:130)
	at org.jboss.ejb.client.remoting.ConfigBasedEJBClientContextSelector.<init>(ConfigBasedEJBClientContextSelector.java:100)
	at org.jboss.ejb.client.remoting.ConfigBasedEJBClientContextSelector.<init>(ConfigBasedEJBClientContextSelector.java:68)
	at org.jboss.ejb.client.EJBClientContext.<clinit>(EJBClientContext.java:81)
	at org.jboss.ejb.client.EJBInvocationHandler.doInvoke(EJBInvocationHandler.java:176)
	at org.jboss.ejb.client.EJBInvocationHandler.invoke(EJBInvocationHandler.java:144)
	at $Proxy0.greet(Unknown Source)
	at org.jboss.ejb.client.GreeterClient.test(GreeterClient.java:11)
	at org.jboss.ejb.client.GreeterClient.main(GreeterClient.java:15)
Exception in thread "main" java.lang.IllegalStateException: EJBCLIENT000025: No EJB receiver available for handling [appName:, moduleName:ejb-invocation-service, distinctName:] combination for invocation context org.jboss.ejb.client.EJBClientInvocationContext@e70e30
	at org.jboss.ejb.client.EJBClientContext.requireEJBReceiver(EJBClientContext.java:727)
	at org.jboss.ejb.client.ReceiverInterceptor.handleInvocation(ReceiverInterceptor.java:116)
	at org.jboss.ejb.client.EJBClientInvocationContext.sendRequest(EJBClientInvocationContext.java:183)
	at org.jboss.ejb.client.EJBInvocationHandler.sendRequestWithPossibleRetries(EJBInvocationHandler.java:253)
	at org.jboss.ejb.client.EJBInvocationHandler.doInvoke(EJBInvocationHandler.java:198)
	at org.jboss.ejb.client.EJBInvocationHandler.doInvoke(EJBInvocationHandler.java:181)
	at org.jboss.ejb.client.EJBInvocationHandler.invoke(EJBInvocationHandler.java:144)
	at $Proxy0.greet(Unknown Source)
	at org.jboss.ejb.client.GreeterClient.test(GreeterClient.java:11)
	at org.jboss.ejb.client.GreeterClient.main(GreeterClient.java:15)
~~~

* stop net interface eth0:0
~~~
ifdown eth0:0
~~~

* Run EJB client Again
~~~
java -cp ejb-invocation-client-remote-1.0.jar:ejb-invocation-service-client.jar:jboss-client.jar org.jboss.ejb.client.GreeterClient
~~~

This time EJB client run fine, with the following output:
~~~
Feb 28, 2014 4:52:16 PM org.jboss.ejb.client.EJBClient <clinit>
INFO: JBoss EJB Client version 1.0.21.Final-redhat-1
Feb 28, 2014 4:52:16 PM org.xnio.Xnio <clinit>
INFO: XNIO Version 3.0.7.GA-redhat-1
Feb 28, 2014 4:52:16 PM org.xnio.nio.NioXnio <clinit>
INFO: XNIO NIO Implementation Version 3.0.7.GA-redhat-1
Feb 28, 2014 4:52:16 PM org.jboss.remoting3.EndpointImpl <clinit>
INFO: JBoss Remoting version 3.2.16.GA-redhat-1
Feb 28, 2014 4:52:17 PM org.jboss.ejb.client.remoting.VersionReceiver handleMessage
INFO: EJBCLIENT000017: Received server version 2 and marshalling strategies [river]
Feb 28, 2014 4:52:17 PM org.jboss.ejb.client.remoting.RemotingConnectionEJBReceiver associate
INFO: EJBCLIENT000013: Successful version handshake completed for receiver context EJBReceiverContext{clientContext=org.jboss.ejb.client.EJBClientContext@659db7, receiver=Remoting connection EJB receiver [connection=org.jboss.ejb.client.remoting.ConnectionPool$PooledConnection@b2c6a6,channel=jboss.ejb,nodename=localhost]} on channel Channel ID f1da0e63 (outbound) of Remoting connection 0163f7a1 to /10.66.192.93:4447
Hello Tester, have a pleasant day!
~~~
