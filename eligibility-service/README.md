# eligibility-service

## Purpose

This SpringBoot application covers the implementation of the eligibility Service, which manages below task.

1) Retrieves the eligibility information for the given accounts and asset IDs

2) Persist eligibility information

## Technologies

| Name       | Used              |
|------------|-------------------|
| Build Tool | Gradle            |
| Language   | Java 17          |
| Framework  | Spring boot       |
| Testing    | Junit,Mockito,Spring Test  |
| Microservices |Spring cloud libraries|
| Gateway |Spring cloud gateway|
| Discovery |Eureka server|

## Contents

Outline the file contents of the repository. It helps users navigate the codebase, build configuration and any related
assets.

| File/folder       | Description                                |
|-------------------|--------------------------------------------|
| `src`             | Source code.                        |
| `.gitignore`      | Define what to ignore at commit time.      |
| `build.gradle`    | The gradle configuration to this project.  |
| `README.md`       | This README file.                          |

### Prerequisites

To run the project you will need to have the following installed:

* Java 17

For information about the software versions used to build this API and a complete list of it's dependencies see
build.gradle

## Building the application

The project uses [Gradle](https://gradle.org) as a build tool. It already contains
`./gradlew` wrapper script, so there's no need to install gradle.

To build the project execute the following command:

```bash
  ./gradlew build
```

**or**

```bash
  gradlew build
```

To clean up your environment use the following, it will delete any temporarily generated files such as reports.

```bash
  ./gradlew clean
```

**or**

```bash
  gradlew clean
```

### Running

Alternatively, you can start the application from the current source files using Gradle as follows:

 ```
 ./gradlew bootRun
 ```

**or**

  ```
 gradlew bootRun
 ```

### Testcase Running

Testsuites can be run with below command.

 ```
 ./gradlew clean test
 ```

**or**

 ```
 gradlew clean test
 ```

## Swagger UI

You can access the Swagger UI for this application at the following URL:

[Swagger UI](http://localhost:8804/swagger-ui/index.html)

## Database

http://localhost:8804/h2-console

ELIGIBILITIES

| Column       | Data Type       | Nullable  |key        |
|--------------|-----------------|-----------|-----------|
| ID           | BIGINT          | No        |Primary Key| 
| ELIGIBLE     | INTEGER         | Yes       |           |
| DISCOUNT     | FLOAT(53)       | Yes       |           |

Relationships The ELIGIBILITY_ACCOUNTIDS and ELIGIBILITY_ASSETIDS tables are linked to the ELIGIBILITIES table through
foreign key constraints. This allows for efficient querying and management of the eligibility data, including the
associated account IDs and asset IDs.

ELIGIBILITY_ACCOUNTIDS

This table stores the main eligibility data, including the eligibility status (eligible column) and the discount value (
discount column).

| Column       | Data Type                      | Nullable  |key
|------------|----------------------------------|-----------|----
| ELIGIBILITY_ID | BIGINT                       |  No       |
| ACCOUNTIDS   | CHARACTER VARYING(255)         |  No       |  Foreign Key(ELIGIBILITIES - ID)   |

ELIGIBILITY_ACCOUNTIDS

This table stores the main eligibility data, including the eligibility status (eligible column) and the discount value (
discount column).

| Column       | Data Type                      | Nullable  |key
|------------|----------------------------------|-----------|----
| ELIGIBILITY_ID | BIGINT                       |  No       |
| ASSETIDS   | CHARACTER VARYING(255)           |  No       |  Foreign Key(ELIGIBILITIES - ID)   |
