# 调试 standalone 启动

standalone.sh 脚本中如下信息:

~~~
   if [ "x$LAUNCH_JBOSS_IN_BACKGROUND" = "x" ]; then
      # Execute the JVM in the foreground
      eval \"$JAVA\" -D\"[Standalone]\" $JAVA_OPTS \
         \"-Dorg.jboss.boot.log.file=$JBOSS_LOG_DIR/server.log\" \
         \"-Dlogging.configuration=file:$JBOSS_CONFIG_DIR/logging.properties\" \
         -jar \"$JBOSS_HOME/jboss-modules.jar\" \
         -mp \"${JBOSS_MODULEPATH}\" \
         -jaxpmodule "javax.xml.jaxp-provider" \
         org.jboss.as.standalone \
         -Djboss.home.dir=\"$JBOSS_HOME\" \
         -Djboss.server.base.dir=\"$JBOSS_BASE_DIR\" \
         "$SERVER_OPTS"
      JBOSS_STATUS=$?
~~~

我们可以看到启动的可执行 module 为 `org.jboss.as.standalone`，我们查看该 module 对应的描述文件 `modules/system/layers/base/org/jboss/as/standalone/main/module.xml` 中 Main 方法如下:

~~~
<module xmlns="urn:jboss:module:1.1" name="org.jboss.as.standalone">
    <main-class name="org.jboss.as.server.Main"/>
~~~

编辑 standalone.conf，添加如下 JVM 调试参数

~~~
JAVA_OPTS="$JAVA_OPTS -agentlib:jdwp=transport=dt_socket,address=8787,server=y,suspend=y"
~~~

在 `org.jboss.as.server.Main` 中添加调试断点开始调试如下：

![standalone startup main](img/standalone-start-main.png)

对照启动脚本，Main 方法中传入了两个参数：

~~~
-Djboss.home.dir=/home/kylin/work/eap/jboss-eap-6.1, 
-Djboss.server.base.dir=/home/kylin/work/eap/jboss-eap-6.1/standalone
~~~
