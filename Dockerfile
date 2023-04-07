FROM openjdk:17-alpine
ADD /target/spring_boot_security_web-0.0.1-SNAPSHOT.jar codingbat.jar
ENTRYPOINT ["java","-jar" , "codingbat.jar"]
EXPOSE 8080