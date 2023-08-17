FROM openjdk:8
EXPOSE 8080

ADD target/webapp-deploy.jar webapp-deploy.jar

ENTRYPOINT [ "java","jar","/webapp-deploy.jar" ]
