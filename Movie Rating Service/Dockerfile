FROM openjdk:8
ADD target/movie-rating-service.jar app.jar
EXPOSE 4000:4000
ENTRYPOINT ["java","-jar","app.jar"]