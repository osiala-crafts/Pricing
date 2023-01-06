## Pricing

This project allows to bill train company customers.

#### Functional resume
Customers are billed basing an input file which contains all customer journeys.  
Those journeys are then transformed on trips and each trip is priced basing on pricing rules configuration.

#### Technical architecture
In this project we are using hexagonal architecture to separate concerns, how the business logic is implemented from how this business logic will be used and what it need to function.  
  
- Application layer (left side / user side) : containing how our billing/pricing business logic is used, in our case we are using main arguments from command line, which can be replace by other adapters like API controllers or others.  
- Domain layer(inside hexagon) : containing business logic implementation.  
- Infrastructure layer(right side / server side) : containing what business logic need to work, in our case we are using file configuration which can be replaced by any other adapter without impacting the other layers. 

![alt text](https://github.com/osiala-crafts/Pricing/blob/master/src/main/resources/spec/arc.PNG?raw=true)

 
#### Build details
This project is built using maven.
- Use mvn clean package to build the project

The project is built using two ways and we will generate two different packages : 
- Manual way : where the project dependencies are generated in a lib folder inside the target folder.
- Aggregated way : where the generated package is generated with their dependencies inside so that we can use our package without providing dependencies. 

#### How to use the generated packages

- Form the target folder  
$ java -jar pricing-1.0.jar "path/to/inputFile" "path/to/outputFile"

- Anywhere in you filesystem  
$ java -jar pricing-1.0-jar-with-dependencies.jar "path/to/inputFile" "path/to/outputFile"


