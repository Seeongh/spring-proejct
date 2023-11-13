FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY target/catalog-service-1.0.jar CateService.jar
ENTRYPOINT ["java","-jar","CateService.jar"]