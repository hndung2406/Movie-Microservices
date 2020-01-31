FROM openjdk:8
ADD target/movie-catalog-service.jar app.jar
EXPOSE 2000:2000
ENTRYPOINT ["java","-jar","app.jar"]