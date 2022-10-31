FROM openjdk:11
VOLUME /tmp
EXPOSE 8005
ADD ./target/items-service-0.0.1-SNAPSHOT.jar service-items.jar
ENTRYPOINT ["java", "-jar", "/service-items.jar"]