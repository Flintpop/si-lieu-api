# Pull base image
FROM tomcat:10.1.19-jdk21-temurin

# Copy to images tomcat path and rename to ROOT.war to deploy at root context
ADD target/si-lieu-api-1.0.war /usr/local/tomcat/webapps/ROOT.war

CMD ["catalina.sh", "run"]
