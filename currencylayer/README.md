# Overview

**Currencylayer Service**

The service is responsible for delivery quotes updates to JMS queue

# Configuration

## Environment variables

| Name | Default value | Description | 
| --- | --- | --- |
| server.port | 8083 | A local bind port |
| currencylayer.api.accesskey | | Currencylayer provider's accesskey |
| currencylayer.api.endpoint | | Currencylayer provider's URL |
    
# Bootstrap

## Local

Run using *Maven spring-boot plugin*:

`$mvn spring-boot:run`

## Cloudfoundry

`$ cf push -p target/manifest.yml`

     