FROM library/tomcat:8.5.35

COPY target/spring-jsql.war /usr/local/tomcat/webapps/spring-jsql.war
