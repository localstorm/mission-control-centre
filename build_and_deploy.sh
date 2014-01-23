#! /bin/sh
ant dist

#cp dist/gtdmcc.war ~/Program_Files/tomcat-w-ejb/webapps
#cp dist/gtdmcc-ejb.jar ~/Program_Files/tomcat-w-ejb/apps
#~/Program_Files/tomcat-w-ejb/bin/shutdown.sh
#rm -f ~/Program_Files/tomcat-w-ejb/logs/*
#rm -rf ~/Program_Files/tomcat-w-ejb/webapps/gtdmcc
#rm -rf ~/Program_Files/tomcat-w-ejb/txlog/*
#sleep 2
#~/Program_Files/tomcat-w-ejb/bin/startup.sh

cp dist/gtdmcc.ear ~/Program_Files/jboss-4.2.3.GA/server/default/deploy

#cp dist/gtdmcc.ear ~/Program_Files/glassfish/domains/domain1/autodeploy



