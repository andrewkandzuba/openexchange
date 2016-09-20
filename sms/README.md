# Overview

**Sms Service**

Test service to verify concurrency aspects of JMS/JPA combination 

# Configuration

## Environment variables

| Name | Default value | Description | 
| --- | --- | --- |
    
# Bootstrap

## Local

Run using *Maven spring-boot plugin*:

`$mvn spring-boot:run`

## Cloudfoundry

`$ cf push -p target/manifest.yml`

     