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

1) In order to install dependencies for the service: ./gradlew clean build
2) Run command ./gradlew pStop wStop to remove all containers for postgres and wiremock
3) In order to run the service: ./gradlew bR
- This command will first start the postgres container, then apply the migration scripts and also start wiremock container
- Wiremock container starts at port :8905

*Docker Notes:
- In order to remove postgres and migration container and associated volumes type the command ./gradlew postgresStop
- In order to remove wiremock container and associated volumes type the command ./gradlew wiremockStop

API endpoints:
---------------------------------------------------------------------------
In order to get the JWT token:
POST http://localhost:8900/api/auth/token with body 

{
    "username": "username",
    "password": "password"
}

Headers:
    User-Agent: ...
    Content-Type: application/json
---------------------------------------------------------------------------
In order to invalidate a refresh token per user agent and logout the user
DELETE http://localhost:8900/api/auth/token
Headers:
    Authorization: Bearer access-token
    User-Agent: ...
    Content-Type: application/json

---------------------------------------------------------------------------
In order to invalidate all refresh tokens of user
DELETE http://localhost:8900/api/auth/token/all
Headers:
    Authorization: Bearer access-token
    Content-Type: application/json
    
---------------------------------------------------------------------------
In order to update access token with refresh token
PUT http://localhost:8900/api/auth/token
Headers:
    Authorization: Bearer refresh-token
    Content-Type: application/json