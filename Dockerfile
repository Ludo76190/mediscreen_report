FROM openjdk:8-jdk-alpine
COPY target/*.jar msreport-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=docker","/msreport-0.0.1-SNAPSHOT.jar"]