FROM openjdk:11
EXPOSE 8080
ADD build/libs/link-shortener-service-0.0.1.jar link-shortener-service-0.0.1.jar
ENTRYPOINT ["java", "-jar", "/link-shortener-service-0.0.1.jar"]