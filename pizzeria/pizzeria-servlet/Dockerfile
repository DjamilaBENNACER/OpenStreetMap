FROM tomcat:9.0-jre11-slim

RUN rm -rf /usr/local/tomcat/webapps/*

COPY build/libs/pizzeria-servlet.war /usr/local/tomcat/webapps/ROOT.war

