#
# Build stage
#
FROM maven:3.9.6-amazoncorretto-21-debian AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

#
# Package stage
#
FROM amazoncorretto:21-alpine-full
COPY --from=build /home/app/target/bookStore-0.0.1-SNAPSHOT.jar /usr/local/lib/bookStore.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","/usr/local/lib/bookStore.jar"]