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

ENDPOINTS DOCUMENTATION
-----------------------
We can view all endpoints from the postman collection in postman-collection folder

CREATE A SELF SIGNED CERTIFICATE
--------------------------------
- The authorization service can accept only connections over https(SSL) we need to create a self signed certificate.
- Java can understand only two types of certificates JKS and PKCS12. In our case we are using the latter one. 
- We need to create a .p12 file under kitkat-authorization-service/authorization-application/src/main/resources/keystore with name (kitkat_auth.p12)

Instructions on how to create valid certificates are in the README.md of kitkat-front-end repo. The .p12 file should be exported from the .pem files of
the front end application and imported in the path mentioned above.