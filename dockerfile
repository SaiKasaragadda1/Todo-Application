FROM openjdk:7
EXPOSE 8081
ADD target/webapp-deploy.jar webapp-deploy.jar
ENTRYPOINT [ "java","-jar","/webapp-deploy.jar" ]