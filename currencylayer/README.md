# Overview

**Batch Service**

The service is responsible for running different periodical batch flows

# Configuration

## Environment variables

| Name | Default value | Description | 
| --- | --- | --- |
| server.port | 8083 | A local bind port |
| spring.batch.job.chunk.size | 8 | A size of the batch |
| jdk.launcher.addmods | | Include into JVM arguments if run on JDK9: java.xml.bind,java.annotations.common |
| currencylayer.api.accesskey | | Currencylayer provider's accesskey |
| currencylayer.api.endpoint | | Currencylayer provider's URL |
    
# Bootstrap

## Local

Run using *Maven spring-boot plugin*:

`$mvn spring-boot:run`

## Cloudfoundry

`$ cf push -p target/manifest.yml`

     