# *Rick & Morty*
## Description:
***Web-application that get data from another API and save it to DB***

## Features:
> - save a movie-character by API with synchronization by time
> - get random movie-character
> - get a movie-character by name

## Structure:
***The application has an N-Tier Architecture.***

> - Controllers layer - allows this application to communicate with users by receiving requests and
    sending responses
> - Service layer - responsible for the business logic of the application
> - Dao layer - responsible for performing database operations (CRUD operations)

## Technologies:
> - Maven
> - PostgreSQL
> - SpringBoot 2.7.5
> - Java 11
> - Liquibase
> - Docker
> - Swagger


## How to run this project:
> - Clone the project
> - Write your properties to application.properties file
> - Run the project

## Example requests
> - http://localhost:8090/movie-characters/random
> - http://localhost:8090/movie-characters/by-name?
# autoservice
