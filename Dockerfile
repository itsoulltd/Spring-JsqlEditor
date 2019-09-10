FROM library/tomcat:8.5.35

RUN rm -rf /usr/local/tomcat/webapps/ROOT
COPY target/ROOT.war /usr/local/tomcat/webapps/
