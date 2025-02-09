FROM openjdk:17-jdk-oracle
COPY build/libs/*jar stayfinder.jar
ENTRYPOINT ["java","-jar","stayfinder.jar"]