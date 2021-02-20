# syntax=docker/dockerfile:experimental
FROM openjdk:15-jdk-alpine AS build

WORKDIR /app

COPY . /app
RUN ./gradlew clean build

FROM openjdk:15-jdk-alpine as prod

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]