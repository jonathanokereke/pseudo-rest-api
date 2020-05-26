# Pseudo-rest-api
RESTful API Implementation of Pseudonymization service - based on Mainzelliste

## Introduction

## Manual Integration
Prerequisite: Java 11, Maven 3.6, Docker 18
 - Build the entire project from the root directory of this repository

    <code>mvn clean install</code>
    
    <code>mvn package</code>
    
  - Start the Mainzelliste server
    
    <code>docker-compose up</code>
    
    By default the Mainzelliste server runs at port <code>8080</code>
    
  - Setup MySQL Server (Local instance)
  
        Host:       localhost
        Port:       3306
        User:       root
        Password:   medic2020
        Database:   pseudo_db
        url:        jdbc:mysql://localhost:3306
        
    Start the MySQL Server with the above credential.
        
    
  - Run the jar (Java Archived) file
  
    <code>java -jar target/pseudo-api-1.0-SNAPSHOT.jar</code>
  
    By default the RESTful API runs at port <code>8082</code>
    
  - Now, you can start consuming the Pseudonymization service at:
  
    <code>http://localhost:8082/api/v2/pseudo-service/ {resource endpoint} </code>
    
Example:

    curl -X POST http://localhost:8082/api/v2/pseudo-service/sessions
       
The above API call will create a <i>session</i> and return HTTP-status code 201 (if successful).

More information about the resources and endpoint can be found on the API Documentation.


## Docker Implementation

<i>Coming soon . . . </i>
