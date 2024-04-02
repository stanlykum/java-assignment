# Collateral Application

## Overall Architecture

![Overall diagram](https://github.com/stanlykum/java-assignment/assets/30554963/c19d4c9e-957a-4903-a304-22f6b2d3799e)

## How to Run
```
1. cd discovery-service

   gradlew bootRun
```
```
2. Navigate to new terminal
   cd ..
   cd api-gateway
   gradlew bootRun
```

```
3. Navigate to new terminal
   cd ..
   cd position-service
   gradlew bootRun
```
```
4. Navigate to new terminal
   cd ..
   cd eligibility-service
   gradlew bootRun
```
```
5. Navigate to new terminal
   cd ..
   cd price-service
   gradlew bootRun
```
```
6. Navigate to new terminal
   cd ..
   cd collateral-service
   gradlew bootRun
```

## Service names and responsibilities

Discovery UI - http://localhost:8801/


| service name       | From API gateway| Swagger UI|
|------------|-------------------|------------------------
| discovery-service |Naming server| |
| api-gate |Entry point for all microservices| 
|collateral-service | Responsible for calculating the collateral value for a given list of account IDs and returning the result as a list of CollateralValue objects|[Swagger UI](http://localhost:8802/swagger-ui/index.html)
| position-service   | Retrieve positions for a given list of account IDs and Persist positions |[Swagger UI](http://localhost:8803/swagger-ui/index.html)
| eligibility-service  | Retrieves the eligibility information for the given accounts and asset IDs and Persist eligibility information |[Swagger UI](http://localhost:8804/swagger-ui/index.html)
| prices-service |Retrieve prices for a given list of asset IDs and Persist prices|[Swagger UI](http://localhost:8805/swagger-ui/index.html)



## Postman collection
Post man collection available in postman-collections folder


| service name       | From API gateway              |
|------------|-------------------|
| collateral-service | http://localhost:8800/api/v1/collaterals/calculate           |
| position-service-get   | http://localhost:8800/api/v1/positions
| position-service-save   |http://localhost:8800/api/v1/positions/save|
| eligibility-service-Get  | http://localhost:8800/api/v1/eligibilities       |
| eligibility-service-save |http://localhost:8800/api/v1/eligibilities/save|
| prices-service-get |http://localhost:8800/api/v1/prices/assetIds|
| prices-service-save |http://localhost:8800/api/v1/prices/save|

## Refer each service folder for detailed steps
