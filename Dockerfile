FROM amazoncorretto:17
EXPOSE 8080
COPY build/libs/test-0.0.1-SNAPSHOT-plain.jar test.jar
ENTRYPOINT ["java", "-jar", "/test.jar"]