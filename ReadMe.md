# Rabobank Statement Validation Process #

### What is this API for? ###

* Quick summary
  Api receives Customer statement in form of XML or CSV files and validate the records based on below conditions
  
     * all transaction references should be unique
     * end balance needs to be validated with mutation value

* Version 1.0

### Run Application in local mode ###

* Clone this repository 
* Run the below command in terminal or right click the project and click the Run as -> maven clean and maven install 
``` 
`mvn clean install`. 
```
* Run as springboot application

```
Request Url for this application : `http://localhost:8080/api/rest/v1/statement/process`. 
```

### SWAGGER UI ###

Swagger Url for this application : `http://localhost:8080/swagger-ui.html`.

### POSTMAN TESTING ###

we can test this application through postman tool also:

steps to access this application through postman:
1.open the postman tool.
2.select the http request type as "POST"
3.type the url - `http://localhost:8080/api/rest/v1/statement/process`.
4.select the Body, selct the key as file and select the csv or xml file in the value tab.
5.press the send button.

You may also package the application in the form of a jar and then run the jar file like so -

### Build Appliation ###
```bash
mvn clean package
java -jar target/CustomerStatementProcessor-1.0.0-SNAPSHOT.jar
```

### Technologies Used ###
a. Java 1.8
b. Spring Boot 2.1.4 - REST Microservices
c. Swagger 2.9.2
d. Junit 4.13
e. Apache Common API
f. Maven