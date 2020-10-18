FROM gradle:jre14-openj9
WORKDIR /usr/src/authorization-application-service
CMD ["./gradlew", "clean", "build"]
CMD ["./gradlew", "bR"]
COPY . .