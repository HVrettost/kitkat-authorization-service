FROM gradle:jre14-openj9
WORKDIR /usr/src/authorization-application-service
# remove below line to create a faster build
CMD ./gradlew clean build
CMD ./gradlew bR
COPY . .