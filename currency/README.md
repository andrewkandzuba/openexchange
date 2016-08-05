# Overview

**Currency Service**

The microservice facilities currencies details and exchange rates statistics. 

# API

| Method | Path | Description | User authenticated | Available from UI |
| --- | :--- | --- | :---: | :---: |
| GET | /currencies/ | Lists all registered currencies | | × |
| GET | /rates/current/{from}/{to} | Returns instant currencies exchange rate |  | × |
| GET | /rates/current/{date}/{from}/{to} | Returns currencies exchange rate at given data |  | × |

# Configuration

## Environment variables

| Name | Default value | Description | 
| --- | --- | --- |
| EUREKA_SERVER_URL | | URL of Eureka Service Registry |
| server.port | 8081 |  A local bind port |

# Bootstrap

## Local

Run using *Maven spring-boot plugin*:

`$mvn spring-boot:run -Drun.jvmArguments='-DEUREKA_SERVER_URL=...'`

## Cloudfoundry

`$ cf push -p target/manifest.yml`

