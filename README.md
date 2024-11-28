# Shortify - URL Shortening Service

Shortify is a URL shortening service developed in Java 17 using Spring Boot and PostgreSQL. This project allows users to shorten long URLs and retrieve them via a short identifier.

## Features

- Shortens long URLs into a short format.
- Stores the relationship between original and shortened URLs in a PostgreSQL database.
- Retrieves the original URL from its shortened version.
- Generates a unique identifier using Base64 encoding.

## Requirements

- Java 17
- PostgreSQL
- Maven (for building and managing dependencies)

## Installation

1. Clone this repository:
   ```bash
   git clone https://github.com/ocollp/shortify.git
   ```
   
2. Navigate to the project directory:
   ```bash
   cd shortify
   ```
   
3. Set up your PostgreSQL database:
* Create a database for the project.
* Ensure that the database is up and running.
   
4. Configure the application.properties file:
* Set the database connection URL and the base URL for the service.

5. Build the project using Maven
   ```bash
   mvn clean install
   ```  

6. Run the application:
   ```bash
   mvn spring-boot:run
   ```  
   
## Usage
* **Shorten a URL**: Send a POST request to http://localhost:8080/shortify with a body containing the original URL.
* **Retrieve an original URL**: Send a GET request to http://localhost:8080/shortify/{shortenedPath}.

## Example Requests
1. **Shorten URL**:
   ```bash
   POST /shortify
   Content-Type: application/json
   
   {
      "originalUrl": "www.example.com/looooooong-url"
   }
   ```  
2. **Retrieve Original URL**:
   ```bash
    GET /shortify/12e6e
   ``` 