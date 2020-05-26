# Pseudo-rest-api
RESTful API Implementation of Pseudonymization service - based on Mainzelliste

## Introduction

## Manual Integration
Prerequisite: Java 11, Maven 3.6, Docker 18
 - Build the entire project from the root directory of this repository


        mvn clean install

        mvn package
    
  - Start the Mainzelliste server
    
    
        docker-compose up
    
    
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
  

        java -jar target/pseudo-api-1.0-SNAPSHOT.jar  

By default the RESTful API runs at port <code>8082</code>
    
  - Now, you can start consuming the Pseudonymization service at:
  
  
        http://localhost:8082/api/v2/pseudo-service/ {resource endpoint}
    
Example:

        curl -X POST http://localhost:8082/api/v2/pseudo-service/sessions
       
The above API call will create a <i>session</i> and return HTTP-status code 201 <i>(if successful)</i>.

More information about the resources and endpoint can be found in the <a href="#">API Documentation</a>.


## Docker Implementation

<i>Coming soon . . . </i>
