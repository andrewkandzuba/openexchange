# Overview

**Cashier Service**

The microservice implements a *cashier* contract.

# API

@ToDo 

# Configuration

## Environment variables

| Name | Default value | Description | 
| --- | --- | --- |
| EUREKA_SERVER_URL | | URL of Eureka Service Registry |
| LOCAL_INSTANCE_PORT | 80 | The local instance's port to be reported to the service registry  |
| server.port | 8081 |  A local bind port |

# Bootstrap

## Local

Run using *Maven spring-boot plugin*:

`$mvn spring-boot:run -Drun.jvmArguments='-DEUREKA_SERVER_URL=...'`
