#Seam Social Test Suite

##Running the testsuite on the default container (Embedded Weld)

    mvn clean verify

##Running the testsuite on JBoss AS 7

    export JBOSS_HOME=/path/to/jboss-as-7.x
    mvn clean verify -Darquillian=jbossas-managed-7

##Running the testsuite on JBoss AS 6

    export JBOSS_HOME=/path/to/jboss-as-6.x
    mvn clean verify -Darquillian=jbossas-managed-6

##Running the testsuite on GlassFish 3.1.1

    $GF_HOME/bin/asadmin start-domain
    mvn clean verify -Darquillian=glassfish-remote-3.1

