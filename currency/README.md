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
| eureka.instance.nonSecurePort | 8081 |  Local binding port |
| spring.datasource.platform | | Datasource platform |
| spring.datasource.url | | Datasource url |
| spring.datasource.username | | Datasource user |
| spring.datasource.password | | Datasource password |

# Bootstrap

## Local

Run using *Maven spring-boot plugin*:

`$mvn spring-boot:run -Drun.jvmArguments='...'`

## Cloudfoundry

`$ cf push -p target/manifest.yml`

