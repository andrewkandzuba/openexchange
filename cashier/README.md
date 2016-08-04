# Overview

**Cashier Service**

The microservice implements a *cashier* contract.

# API

@ToDo 

# Configuration

## Environment variables

| Name | Default value | Description | 
| --- | --- | --- |
| CONFIG_SERVER_PASSWORD | | A password of global Configuration Service | 
| EUREKA_SERVER_URL | | URL of Eureka Service Registry |
| server.port | 8080 | Unsecured local bind port |

# Bootstrap

## Local

Run using *Maven spring-boot plugin*:

`$mvn spring-boot:run -Drun.jvmArguments='-DEUREKA_SERVER_URL=... -DCONFIG_SERVER_PASSWORD=...'`
