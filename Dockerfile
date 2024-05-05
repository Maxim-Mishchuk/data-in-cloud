FROM openjdk:17-alpine
LABEL authors="laurenci"
COPY build/libs/data-in-cloud-0.0.1-SNAPSHOT.jar project.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "project.jar"]