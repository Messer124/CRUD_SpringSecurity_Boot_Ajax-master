FROM adoptopenjdk/openjdk11
COPY target/*.jar pre_3_1_1-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/pre_3_1_1-0.0.1-SNAPSHOT.jar"]