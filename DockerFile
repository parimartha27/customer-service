FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY build/libs/customer-service-0.0.1-SNAPSHOT.jar customer-service.jar

EXPOSE 5002

ENTRYPOINT ["java","-jar","customer-service.jar"]


