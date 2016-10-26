# Overview

**Currency Service**

The microservice facilities currencies details and exchange quotes statistics. 

# API

| Method | Path | Description | User authenticated | Available from UI |
| --- | :--- | --- | :---: | :---: |
| GET | /currencies/ | Lists all registered currencies | | × |
| GET | /currencies/{code} | Get details of a certain currency | | × |
| GET | /quotes/{source}/{target} | Get an exchange's quote of a source to target currencies  |  | × |

# Configuration

## Environment variables

| Name | Default value | Description | 
| --- | --- | --- |
| eureka.instance.nonSecurePort | 8081 |  Local binding port |
| spring.datasource.platform | | Datasource platform |
| spring.datasource.url | | Datasource url |
| spring.datasource.username | | Datasource user |
| spring.datasource.password | | Datasource password |
| jdk.launcher.addmods | | Include into JVM arguments if run on JDK9: java.xml.bind,java.annotations.common |

# Bootstrap

## Local

Run using *Maven spring-boot plugin*:

`$mvn spring-boot:run -Drun.jvmArguments='...'`

## Cloudfoundry

`$ cf push -p target/manifest.yml`

