# kitkat-authorization-service

Authorization/Authentication Service using JWTs to secure subsequent api calls. This microservice is dockerized and
contains different profile configuration(local-dev, integrationpdev, prod, e.t.c). 

Technologies:
* Postgres
* Spring Boot
* Spring JPA
* Spring security
* Java JWT -> JAVA Json Web Tokens implementation
* Flyway for database migration scripts
* Pitest for mutation testing
* Spock as a testing framework
* Docker
* Lombock library

What you will need before starting the service:
1) Install JAVA 14 using sdk man from sdkman.io
2) Install Docker 

1) In order to install dependencies for the service: ./gradlew clean build
2) Run command ./gradlew pStop wStop to remove all containers for postgres and wiremock
3) In order to run the service: ./gradlew bR
- This command will first start the postgres container, then apply the migration scripts and also start wiremock container
- Wiremock container starts at port :8905

* Docker Notes:
- In order to remove postgres and migration container and associated volumes type the command ./gradlew postgresStop
- In order to remove wiremock container and associated volumes type the command ./gradlew wiremockStop

ENDPOINTS DOCUMENTATION
-----------------------
We can view all endpoints from the postman collection in postman-collection folder

CREATE A SELF SIGNED CERTIFICATE
--------------------------------
- The authorization service can accept only connections over https(SSL) we need to create a self signed certificate for local development.
- Java can understand only two types of certificates JKS and PKCS12. In our case we are using the latter one. 
- We need to create a .p12 file under kitkat-authorization-service/authorization-application/src/main/resources/keystore with name (kitkat_auth.p12)
*Notice: If we do not want to use a certificate just go to application.properties for active spring profile used and set server.ssl.enabled to false

General Idea of this approach using JWT.
----------------------------------------
This approach uses short lived JWT tokens (approximately 3 to 5 minutes). After the user authenticates successfully
a JWT access token is generated along with a refresh token and are both returned to the user in a cookie
which is Http-Only and Secure. 

This will prevent XSS attacks as the client will not be able to use it in Javascript code. Also it will be transmitted
over https preventing a 'Man In The Middle' attack as it will be encrypted with TLS secure protocol. 

Every subsequent call will use the cookie containing the access and refresh token to use the API endpoints. Of course the
authorization part will only contain the access token. The refresh token will be there because the way it is transmitted
it will not be possible to be manipulated by the client side. 


With this approach we will not need to create a blacklist In Memory database that will be looked up every time in every authorization call. 
We only have to make an access token update every 3 to 5 minutes to get a new token. Only then we will access 
the database that will check if the refresh token exists and issue a new access token to the user. 

If the user decides to logout the cookie will be erased but the downside is the fact that the access token will be valid for the
remaining time until the expiration.

API documentation
-----------------

At the moment the service does not contain a SWAGGER documentation. It although has a POSTMAN collection that can be 
advised.

NOTICE
------
Keep in mind that this service is work in progress and will have more extensions in the near future like SSO auth and
a more complete Functional Test suite.
