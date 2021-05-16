# aclue-shop-service
Demo project for a spring boot live demo with focus on testing and security.

## What do we have?
- Spring Boot (Java 11 / Maven)
- Spring Web
- Spring Data JPA
- Lombok
- Flyway
- DevTools

## What do we develop together?
- integrationtest setup with H2 in-memory database
- serving a REST API
    - REST controller
    - tests for the controller with MockMvc
- requesting a remote client
    - client calls with RestTemplate
    - refactoring the client url to properties
    - testing the client calls with Wiremock
- mocking with Mockito
- Applying security
    - securing the application with a basic auth user (authentication)
    - apply authorization on path level
    - apply authorization on method level
    - testing security
        - with a real (basic auth) user
        - with a mock user

## Scenario
- Shop-System
- existing entity: Order
    - id
    - article (just one, to keep it simple)
    - status (CREATED, CONFIRMED, ARTICLE_RESERVED, PAYED)
    - (customer)
- PUT /order/{id} 
    - allows updating status to CONFIRMED
    - calls warehouse-service to reserve article for customer
    - calls payment-service via payment-adapter to confirm payment

# Setup
Start local Postgres database via docker:
    
    docker run --name some-postgres -p 5432:5432 -e POSTGRES_PASSWORD=mysecretpassword -d postgres
