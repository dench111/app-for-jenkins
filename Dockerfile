FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY /var/jenkins_home/workspace/sources/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
