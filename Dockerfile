FROM openjdk:17-alpine
ADD /target/Project-0.0.1-SNAPSHOT.jar daily_work.jar
ENTRYPOINT ["java","-jar" , "daily_work.jar"]
EXPOSE 8080