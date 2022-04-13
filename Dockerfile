FROM openjdk:15-jdk-buster
ARG JAR_FILE=build/libs/bank-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]