FROM openjdk:15
COPY target/TestStudents-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]