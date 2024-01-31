FROM openjdk:17-alpine

WORKDIR /app

ENV MONGODB_URI=mongodb://host.docker.internal:27017/twitter_clone_db

CMD ["./gradlew", "clean", "bootJar"]

COPY build/libs/Analog-of-Twitter-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]