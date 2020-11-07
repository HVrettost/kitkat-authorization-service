# kitkat-authorization-service

Authorization Service using JWTs to secure subsequent api calls

Technologies:
* Postgres
* Spring Boot
* Spring JPA
* Spring security using OAauth2
* Java JWT -> JAVA Json Web Tokens implementation
* Flyway for database migration scripts

What you will need before starting the service:
1) Install JAVA 14 using sdk man from sdkman.io
2) Install Docker 

In order to install dependencies for the service: ./gradlew clean build
In order to run the service: ./gradlew bR
- This command will first start the postgres container, then apply the migration scripts and also start wiremock container
- Wiremock container starts at port :8905

In order to get the JWT token:
just make a POST call in http://localhost:8900/api/auth/token with body 

{
    "username": "username",
    "password": "password"
}

*Docker Notes:
- In order to remove postgres and migration container and associated volumes type the command ./gradlew postgresStop
- In order to remove wiremock container and associated volumes type the command ./gradlew wiremockStop

