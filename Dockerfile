FROM openjdk:17-alpine
COPY ./target/spring-batch-demo.jar /usr/app/spring-batch-demo.jar
WORKDIR /usr/app
EXPOSE 8080:8080
CMD ["java","-jar","spring-batch-demo.jar"]