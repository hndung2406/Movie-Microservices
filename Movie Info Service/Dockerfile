FROM openjdk:8
ADD target/movie-info-service.jar app.jar
EXPOSE 3000:3000
ENTRYPOINT ["java","-jar","app.jar"]