If we have deployed Test.war to JBoss, then we enter 
http://xxx.xxx.xx/Test/mypic/a.jpg
we can access a.jpg, Note that, a.jpg not packaged in Test.war, it placed in Server machine which ran the JBoss server.

Weblogic's virtual-directory-mapping can do this, more details as http://docs.oracle.com/cd/E11035_01/wls100/webapp/weblogic_xml.html#wp1039396

