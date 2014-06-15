# 概述

本文根据社区文档 [Extending JBoss AS 7](https://docs.jboss.org/author/display/AS72/Example+subsystem) 扩展 JBoss 7。通过本文我们可以更好的理解 JBoss 底层，本文包括如下内容：

* 创建初始工程
* 创建 schema

# 创建初始工程

通过如下 mvn 命令创建初始化工程：

~~~
mvn archetype:generate -DarchetypeArtifactId=jboss-as-subsystem -DarchetypeGroupId=org.jboss.as.archetypes -DarchetypeVersion=7.1.1.Final -DarchetypeRepository=http://repository.jboss.org/nexus/content/groups/public
~~~

mvn 会提示输入相关的信息，如下所示：

~~~
Define value for property 'groupId': : com.acme.corp
Define value for property 'artifactId': : acme-subsystem
Define value for property 'version':  1.0-SNAPSHOT: : 1.0-SNAPSHOT
Define value for property 'package':  com.acme.corp: : com.acme.corp.tracker
Define value for property 'module': : com.acme.corp.tracker
~~~

mvn 命令执行成功后会产生 acme-subsystem 为初始化的项目，我们可以编译将该项目导入 eclipse 方便后续步骤。通过 mvn install 编译此项目会有如下错误：

~~~
Tests in error: 
  testInstallIntoController(com.acme.corp.tracker.extension.SubsystemParsingTestCase): Can't find bundle for base name com.acme.corp.tracker.extension.LocalDescriptions, locale en_US
  testParseAndMarshalModel(com.acme.corp.tracker.extension.SubsystemParsingTestCase): Can't find bundle for base name com.acme.corp.tracker.extension.LocalDescriptions, locale en_US
  testDescribeHandler(com.acme.corp.tracker.extension.SubsystemParsingTestCase): Can't find bundle for base name com.acme.corp.tracker.extension.LocalDescriptions, locale en_US
  testSubsystemRemoval(com.acme.corp.tracker.extension.SubsystemParsingTestCase): Can't find bundle for base name com.acme.corp.tracker.extension.LocalDescriptions, locale en_US
  testSubsystem(com.acme.corp.tracker.extension.SubsystemBaseParsingTestCase): Can't find bundle for base name com.acme.corp.tracker.extension.LocalDescriptions, locale en_US
~~~

解决此问题的方法是移动 `src/main/resources/com/acme/corp/tracker/subsystem/extension/LocalDescriptions.properties` 到 `src/main/resources/com/acme/corp/tracker/extension/LocalDescriptions.properties`

# 创建 schema

重命名 `src/main/resources/schema/mysubsystem.xsd` 为 `src/main/resources/schema/acme.xsd`，编辑 `acme.xsd` 内容如下：

~~~
<xs:schema xmlns:xs="http://www.w3.org/2001/XMLSchema"
            targetNamespace="urn:com.acme.corp.tracker:1.0"
            xmlns="urn:com.acme.corp.tracker:1.0"
            elementFormDefault="qualified"
            attributeFormDefault="unqualified"
            version="1.0">
 
   <!-- The subsystem root element -->
   <xs:element name="subsystem" type="subsystemType"/>
   <xs:complexType name="subsystemType">
      <xs:all>
         <xs:element name="deployment-types" type="deployment-typesType"/>
      </xs:all>
   </xs:complexType>
   <xs:complexType name="deployment-typesType">
      <xs:choice minOccurs="0" maxOccurs="unbounded">
         <xs:element name="deployment-type" type="deployment-typeType"/>
      </xs:choice>
   </xs:complexType>
   <xs:complexType name="deployment-typeType">
      <xs:attribute name="suffix" use="required"/>
      <xs:attribute name="tick" type="xs:long" use="optional" default="10000"/>
   </xs:complexType>
</xs:schema>
~~~

编辑 `com.acme.corp.tracker.extension.SubsystemExtension`，确保 NAMESPACE 与 schema 中定义的对应：

~~~
public class SubsystemExtension implements Extension {

    /**
     * The name space used for the {@code substystem} element
     */
    public static final String NAMESPACE = "urn:com.acme.corp.tracker:1.0";
~~~
