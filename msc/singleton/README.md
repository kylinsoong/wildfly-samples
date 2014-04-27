What's this?
------------

In JBoss EAP 5, HA singleton archives were deployed in the deploy-hasingleton/ directory separate from other deployments. This was done to prevent automatic deployment and to ensure the HASingletonDeployer service controlled the deployment and deployed the archive only on the master node in the cluster. There was no hot deployment feature, so redeployment required a server restart. Also, if the master node failed requiring another node to take over as master, the singleton service had to go through the entire deployment process in order to provide the service.

In JBoss EAP 6 this has changed. Using a SingletonService, the target service is installed on every node in the cluster but is only started on one node at any given time. This approach simplifies the deployment requirements and minimizes the time required to relocate the singleton master service between nodes.

Procedure for Implement an HA Singleton
---------------------------------------

### Install & Start EAP 6

Install EAP 6 via:

~~~
unzip jboss-eap-6.2.0.zip -d node1
unzip jboss-eap-6.2.0.zip -d node2
~~~

Create Management User and Application User with guest role on each node via `add_user.sh`

* Management User: admin/password1!
* Application User: ejbclient/password1!

Start EAP 6 via:

~~~
./standalone.sh -server-config=standalone-ha.xml -Djboss.node.name=node1
./standalone.sh -server-config=standalone-ha.xml -Djboss.node.name=node2 -Djboss.socket.binding.port-offset=100
~~~

### Build & Deploy

~~~
mvn clean install
cp wildfly-ha-singleton-service.jar .../node1/standalone/deployments/
cp wildfly-ha-singleton-service.jar .../node2/standalone/deployments/
~~~

### Run & Test

Run `SingletonServiceClient` as java application, the active service always locate on one server, the output looks:

~~~
#0 The service is active on node with name = node2
#1 The service is active on node with name = node2
#2 The service is active on node with name = node2
#3 The service is active on node with name = node2
~~~
