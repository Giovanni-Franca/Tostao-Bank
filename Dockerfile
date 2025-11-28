FROM openjdk:17-jdk-slim

COPY target/*.jar app.jar

RUN apt-get install maven -y
RUN mvn clean install

EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "app.jar" ]