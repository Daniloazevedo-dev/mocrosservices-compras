FROM maven:3.6.3-jdk-11 as build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -DskipTests.test.skip=true

FROM openjdk:11
COPY --from=build /home/app/target/app.war /usr/local/lib/app.war
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/app.war"]
