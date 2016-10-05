# Overview

**Registry Service**

The main registry of all microservices are run in the system at the moment. 
In addition to regular services it also contains a special `configserver` record points to the *Configuration Server* running instance.
The implementation bases on *Spring Cloud Netflix Eureka* service registry.

# Configuration

## Environment variables

| Name | Default value | Description | 
| --- | --- | --- |
| server.port | 8761 | A local bind port |
| jdk.launcher.addmods | | Include into JVM arguments if run on JDK9: java.xml.bind,java.annotations.common |

# Bootstrap

## Local

Run using *Maven spring-boot plugin*:

`$mvn spring-boot:run`

## Cloudfoundry

`$ cf push -p target/manifest.yml`

     