# Overview

**Cashier Service**

The microservice implements a *cashier* contract.

# API

@ToDo 

# Configuration

## Environment variables

| Name | Default value | Description | 
| --- | --- | --- |
| eureka.instance.nonSecurePort | 8081 |  Local binding port |



# Bootstrap

## Local

Run using *Maven spring-boot plugin*:

`$mvn spring-boot:run -Drun.jvmArguments='-DEUREKA_SERVER_URL=...'`

## Cloudfoundry

`$ cf push -p target/manifest.yml`
