FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY /tmp/workspace/sources/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
