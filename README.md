Title: DVN-Mavenization Project
last updated: 2013-02-12 Tu
author: Akio Sone at UNC-Odum Institute

1. How to create the skeletal directory-structure: A flat case
1. 1 Ultimate project structure: 


--/DVN-root--+--/DVN-EAR--------+--/src
             |                  +--pom.xml
             |            
             +--/DVN-EJB--------+--/src
             |                  +--pom.xml
             |
             +--/DVN-web--------+--/src
             |                  +--pom.xml
             |
             +--/DVN-ingest-----+--/src
             |                  +--pom.xml
             |
             +--/unf------------+--/src
             |                  +--pom.xml
             |
             +--/network_utils--+--/src
             |                  +--pom.xml
             |                            
             +--pom.xml
                            

Note 1: As of version 3.3, the ultimate artifact of DVN-project is a war file, not an ear file due to the circular dependency between DVN-EJB and DVN-web projects; However, the ear-generating sub-project is kept here.
Note 2: DVN-EJB project was later removed from the project structure because of the above circular dependency

1.2 How to create the above directory structure

Step 1: move to a directory where the DVN-root project is to be created

    e.g., 
    C:\Users\asone>cd C:\ahome\dvn\maven
    C:\ahome\dvn\maven>

Step 2: create the root project

    (1) select the archetype (pom-root) interactively

        C:\ahome\dvn\maven>mvn archetype:generate

        Choose archetype:

        427: remote -> org.codehaus.mojo.archetypes:nbm-archetype (Archetype for development of NetBeans modules in Maven.)
        428: remote -> org.codehaus.mojo.archetypes:nbm-osgi-archetype (Archetype for development of NetBeans modules that can depend on OSG
        i bundles.)
        429: remote -> org.codehaus.mojo.archetypes:nbm-suite-root (Root project archetype for creating multi module projects developing Net
        Beans IDE modules. Approximately similar in functionality to module suites in NetBeans Ant projects.)
        430: remote -> org.codehaus.mojo.archetypes:netbeans-platform-app-archetype (Archetype for sample application based on NetBeans Plat
        form. Creates parent POM with branding and empty NBM project.)
        431: remote -> org.codehaus.mojo.archetypes:osgi-archetype (Archetype for development of OSGi bundles using Apache Felix Maven plugi
        n)
        432: remote -> org.codehaus.mojo.archetypes:pom-root (Root project archetype for creating multi module projects)

    (2) filter the above list by the keyword 'pom-root'

        Choose a number or apply filter (format: [groupId:]artifactId, case sensitive contains): 247: pom-root

    (3) select 1

        Choose archetype:
        1: remote -> org.codehaus.mojo.archetypes:pom-root (Root project archetype for creating multi module projects)
        Choose a number or apply filter (format: [groupId:]artifactId, case sensitive contains): : 1

    (4) select the latest version, 1.1

        Choose org.codehaus.mojo.archetypes:pom-root version:
        1: 1.0
        2: 1.0.1
        3: 1.1
        Choose a number: 3:

    (5) type groupId
        Define value for property 'groupId': : edu.harvard.iq.dvn

    (6) type artifactId
        Define value for property 'artifactId': : DVN-root

    (7) type version
        Define value for property 'version':  1.0-SNAPSHOT: : 3.3

    (8) hit the return key to select the default

        Define value for property 'package':  edu.harvard.iq.dvn: :

    (9) hit the return key to select the default

        Confirm properties configuration:
        groupId: edu.harvard.iq.dvn
        artifactId: DVN-root
        version: 3.3
        package: edu.harvard.iq.dvn
         Y: :

        [INFO] ----------------------------------------------------------------------------
        [INFO] Using following parameters for creating project from Archetype: pom-root:1.1
        [INFO] ----------------------------------------------------------------------------
        [INFO] Parameter: groupId, Value: edu.harvard.iq.dvn
        [INFO] Parameter: artifactId, Value: DVN-root
        [INFO] Parameter: version, Value: 3.3
        [INFO] Parameter: package, Value: edu.harvard.iq.dvn
        [INFO] Parameter: packageInPathFormat, Value: edu/harvard/iq/dvn
        [INFO] Parameter: package, Value: edu.harvard.iq.dvn
        [INFO] Parameter: version, Value: 3.3
        [INFO] Parameter: groupId, Value: edu.harvard.iq.dvn
        [INFO] Parameter: artifactId, Value: DVN-root
        [INFO] project created from Archetype in dir: C:\ahome\dvn\maven\DVN-root
        [INFO] ------------------------------------------------------------------------
        [INFO] BUILD SUCCESS
        [INFO] ------------------------------------------------------------------------
        [INFO] Total time: 3:36.085s
        [INFO] Finished at: Mon Jan 28 13:36:53 EST 2013
        [INFO] Final Memory: 10M/309M
        [INFO] ------------------------------------------------------------------------

        C:\ahome\dvn\maven>ls
        DVN-root

    (10) move into DVN-root directory

        C:\ahome\dvn\maven>cd DVN-root

    (11) ensure that the pom.xml for DVN-root project has been created

        C:\ahome\dvn\maven\DVN-root>ls
        pom.xml

        C:\ahome\dvn\maven\DVN-root>cat pom.xml

        <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
          <modelVersion>4.0.0</modelVersion>
          <groupId>edu.harvard.iq.dvn</groupId>
          <artifactId>DVN-root</artifactId>
          <version>3.3</version>
          <packaging>pom</packaging>
          <name>DVN-root</name>
        </project>


    (12) modify the pom.xml

    (12-1) repository

    (12-2) sub-projects
            <modules>
                <!--<module>DVN-EJB</module>-->
                <module>DVN-web</module>
                <module>DVN-EAR</module>
                <module>DVN-ingest</module>
                <module>unf</module>
                <module>network_utils</module>
          </modules>

    (12-3) add the following dependency block to the pom.xml to copy 
           [see the pom.xml]

    (12-4) add the following execution block to the pom.xml so that non-public jars, which are used by DVN sub-projects, are copied to the local maven repository
            [see the pom.xml]


/* 
************************************************
Disregard this step because DVN-EJB is not used

Step 3 Create the DVN-EJB project under DVN-root

        C:\ahome\dvn\maven\DVN-root>mvn --errors -X archetype:generate -DarchetypeGroupId=org.codehaus.mojo.archetypes -DarchetypeArtifactId
        =ejb-javaee6 -DgroupId=edu.harvard.iq.dvn -DartifactId=DVN-EJB -Dversion=3.3


        Confirm properties configuration:
        groupId: edu.harvard.iq.dvn
        artifactId: DVN-EJB
        version: 3.3
        package: edu.harvard.iq.dvn
         Y: :

        C:\ahome\dvn\maven\DVN-root>ls
        DVN-EJB  pom.xml

************************************************ 
*/

Step 4 Create the DVN-web project under DVN-root

        C:\ahome\dvn\maven\DVN-root>mvn --errors -X archetype:generate -DarchetypeGroupId=org.codehaus.mojo.archetypes -DarchetypeArtifactId
        =webapp-javaee6 -DgroupId=edu.harvard.iq.dvn -DartifactId=DVN-web  -Dversion=3.3

        Confirm properties configuration:
        groupId: edu.harvard.iq.dvn
        artifactId: DVN-web
        version: 3.3
        package: edu.harvard.iq.dvn
         Y: :

Step 5 Create the DVN-EAR project under DVN-root

        C:\ahome\dvn\maven\DVN-root>mvn --errors -X archetype:generate -DarchetypeGroupId=org.codehaus.mojo.archetypes -DarchetypeArtifactId
        =ear-javaee6 -DgroupId=edu.harvard.iq.dvn -DartifactId=DVN-EAR  -Dversion=3.3

        Confirm properties configuration:
        groupId: edu.harvard.iq.dvn
        artifactId: DVN-EAR
        version: 3.3
        package: edu.harvard.iq.dvn
         Y: :

        C:\ahome\dvn\maven\DVN-root>ls
        DVN-EAR  DVN-web  pom.xml

Step 6 Create the DVN-ingest project under DVN-root

        C:\ahome\dvn\maven\DVN-root>mvn --errors -X archetype:generate -DarchetypeGroupId=org.codehaus.mojo.archetypes -DarchetypeArtifactId
        =maven-archetype-quickstart -DgroupId=edu.harvard.iq.dvn -DartifactId=DVN-ingest  -Dversion=3.3

        Choose archetype:

        247: remote -> org.apache.maven.archetypes:maven-archetype-quickstart (An archetype which contains a sample Maven project.)

        Choose a number or apply filter (format: [groupId:]artifactId, case sensitive contains): 247:
        Choose org.apache.maven.archetypes:maven-archetype-quickstart version:
        1: 1.0-alpha-1
        2: 1.0-alpha-2
        3: 1.0-alpha-3
        4: 1.0-alpha-4
        5: 1.0
        6: 1.1
        Choose a number: 6:

        Confirm properties configuration:
        groupId: edu.harvard.iq.dvn
        artifactId: DVN-ingest
        version: 3.3
        package: edu.harvard.iq.dvn
         Y: : y

        C:\ahome\dvn\maven\DVN-root>ls
        DVN-EAR     DVN-ingest  DVN-web     pom.xml


Step 7 create the unf project

        C:\ahome\dvn\maven\DVN-root>mvn --errors -X archetype:generate -DarchetypeGroupId=org.codehaus.mojo.archetypes -DarchetypeArtifactId
        =maven-archetype-quickstart -DgroupId=edu.harvard.iq.dvn -DartifactId=unf  -Dversion=5.0
        [INFO] No archetype defined. Using maven-archetype-quickstart (org.apache.maven.archetypes:maven-archetype-quickstart:1.0)
        Choose archetype:

        247: remote -> org.apache.maven.archetypes:maven-archetype-quickstart (An archetype which contains a sample Maven project.)


        Choose a number or apply filter (format: [groupId:]artifactId, case sensitive contains): 247:
        Choose org.apache.maven.archetypes:maven-archetype-quickstart version:
        1: 1.0-alpha-1
        2: 1.0-alpha-2
        3: 1.0-alpha-3
        4: 1.0-alpha-4
        5: 1.0
        6: 1.1
        Choose a number: 6:
        Confirm properties configuration:
        groupId: edu.harvard.iq.dvn
        artifactId: unf
        version: 5.0
        package: edu.harvard.iq.dvn
         Y: :

        C:\ahome\dvn\maven\DVN-root>ls
        DVN-EAR     DVN-ingest  DVN-web     pom.xml     unf

        C:\ahome\dvn\maven\DVN-root>

Step 8 Copy the network_utils project under DVN-root 
    
    Because network_utils project is already a maven project, its source tree (from "network_utils" directory) can be directly copied as a maven project.
    
    (1) copy its source tree (starting from "network_utils" directory) nto ../DVN-root directory




1.3 copying source files to dvn maven projects respectively


Step 1: network_utils project

    (1) modify the contents of pom.xml

    (1-1) add missing version tag to 3 build-plugins: choose the latest one so that Maven's warning messages are erased
        [see the pom.xml]
    
    (1-2) add the following parent-tag block to its pom.xml

        <parent>
        <artifactId>DVN-root</artifactId>
        <groupId>edu.harvard.iq.dvn</groupId>
        <version>3.3</version>
        </parent>


Step 2: unf project

    (1) remove automatically created java files such as App.java and AppTest.java in dvn directory (under main and test directories)

    (2) copy the source tree of UNF5 project (../dvn/UNF5/trunk/src/edu/harvard/iq/dvn/unf) to ../DVN-root/unf/src/main/java/edu/harvard/iq/dvn

    (3) add the following parent-tag block to its pom.xml

        <parent>
        <artifactId>DVN-root</artifactId>
        <groupId>edu.harvard.iq.dvn</groupId>
        <version>3.3</version>
        </parent>

    (3) add the following dependency tag to the pom.xml
        [see the pom.xml]


Step 3: DVN-ingest project

    (1) remove automatically created java files such as App.java and AppTest.java in dvn directory (under main and test directories)

    (2) To copy the two source sub-trees of DVN-ingest project (ingest and rserve under ../dvn/dvn-app/trunk/src/DVN-ingest/src/edu/harvard/iq/dvn) to ../DVN-root/DVN-ingest/src/main/java/edu/harvard/iq/dvn

    (3) add the following parent-tag block to its pom.xml

        <parent>
        <artifactId>DVN-root</artifactId>
        <groupId>edu.harvard.iq.dvn</groupId>
        <version>3.3</version>
        </parent>

    (4) add the following DVN-ingest-specific dependency tags to the pom.xml
        [see the pom.xml]


Step 4: DVN-web project

    (1) remove automatically created java files such as App.java and AppTest.java in dvn directory (under main and test directories) and index.jsp from ../DVN-root/DVN-web/src/main/webapp

    (2) copy source trees from DVN-EJB project

    (2-1) copy the two source sub-trees of DVN-EJB project (core and ingest under ../dvn/dvn-app/trunk/src/DVN-EJB/src/java/edu/harvard/iq/dvn) to ../DVN-root/DVN-web/src/main/java/edu/harvard/iq/dvn

    (2-2) if it is still used, copy a non-edu package of DVN-EJB project (lia under ../dvn/dvn-app/trunk/src/DVN-EJB/src/java) to ../DVN-root/DVN-web/src/main/java/

    (2-3) copy mime.types and services directory in ../dvn-app/trunk/src/DVN-EJB/src/java/META-INF
        to ../DVN-root/DVN-web/src/main/java/META-INF

    (2-4) copy persistence.xml in ../dvn-app/trunk/src/DVN-EJB/src/conf 
        to  ../DVN-root/DVN-web/src/main/java/META-INF


    (3) copy two subdirectories (api and core) under /dvn-app/trunk/src/DVN-web/src/edu/harvard/iq/dvn
        to ../DVN-root/DVN-web/src/main/java/edu/harvard/iq/dvn 

    (4)copy properties files in ../dvn-app/trunk/src/DVN-web/src
        to ../DVN-root/DVN-web/src/main/java

    (5) copy all files and directories under ../dvn-app/trunk/src/DVN-web/web
        to ../DVN-root/DVN-web/src/main/webapp

    (6) modify the pom.xml

    (6-1) add the following parent-tag block to the pom.xml

        <parent>
        <artifactId>DVN-root</artifactId>
        <groupId>edu.harvard.iq.dvn</groupId>
        <version>3.3</version>
        </parent>

    (6-2) add the following repository tags to the pom.xml of the project because icefaces and seams jars are not available from the maven central repository:

        <repositories>
            <repository>
                <id>jboss-public-repository-group</id>
                <name>JBoss Public Maven Repository Group</name>
                <url>http://repository.jboss.org/nexus/content/groups/public</url>
            </repository>

            <repository>
                <id>jboss-releases</id>
                <name>JBoss Release Repository</name>
                <url>https://repository.jboss.org/nexus/content/repositories/releases</url>
            </repository>
            <repository>
                <url>http://anonsvn.icefaces.org/repo/maven2/releases/</url>
                <id>icefaces2-core</id>
                <layout>default</layout>
                <name>Repository for library ICEfaces Core (2.0.2)</name>
            </repository>
            <repository>
                <id>ICEfaces Repo</id>
                <name>ICEfaces Repo</name>
                <url>http://anonsvn.icefaces.org/repo/maven2/releases/</url>
            </repository>
        </repositories>

    (6-3) add the following dependency tags to the pom.xml
    [see the pom.xml]


    (6-4) add the following pom fragment under build tag of the pom.xml

            <resources>
                <resource>
                    <directory>src/main/java</directory>
                    <includes>
                        <include>*.properties</include>
                        <include>META-INF/*.*</include>
                        <include>META-INF/services/*.*</include>
                    </includes>
                </resource>
            </resources>


2. How to build DVN-root project

Step 1: update properties in the pom.xml of DVN-root project according your build settings concerning the locations of non-public jars, namely, the values of the following tags:

        dvn-non-public-jar-root-dir
        dvn-lib-EJB-dir
        dvn-lib-web-dir
        dvn-lib-COMMON-dir

    [the pom.xml fragment]

    <properties>
        <dvn-non-public-jar-root-dir>C:/ahome/dvn-src/v3_3/dvn-app/trunk/lib</dvn-non-public-jar-root-dir>
        <dvn-lib-EJB-dir>dvn-lib-EJB</dvn-lib-EJB-dir>
        <dvn-lib-web-dir>dvn-lib-WEB</dvn-lib-web-dir>
        <dvn-lib-COMMON-dir>dvn-lib-COMMON</dvn-lib-COMMON-dir>
    </properties>

Step 1: open DVN-root project within the Projects window of Netbeans and right-click the project with the option "Clean and Build" or from the commandline, issue the following command, 

        mvn clean install

    and confirm that the build succeeded
