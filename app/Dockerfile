FROM openjdk:15-jdk-alpine

COPY ./build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]