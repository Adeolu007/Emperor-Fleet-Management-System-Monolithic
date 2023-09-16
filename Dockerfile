FROM openjdk:17-jdk-alpine
ARG JAR-FILE=build/*.jar
COPY ./build/libs/Emperor-Fleet-Vehicle-Management-System-Mono-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 80
#LABEL authors="Adeolu.Odunuyi"

ENTRYPOINT ["java", "-jar","/app.jar"]