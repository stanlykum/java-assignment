# discovery-service

## Purpose

To register all services which takes care of load balancing.

## Technologies

| Name       | Used              |
|------------|-------------------|
| Build Tool | Gradle            |
| Language   | Java 17          |
| Framework  | Spring boot       |
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

## Eureka UI

You can access the Eureka UI  at the following URL:

[Discovery UI](http://localhost:8801/)
