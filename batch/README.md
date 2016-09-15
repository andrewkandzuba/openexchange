# Overview

**Batch Service**

The service is responsible for running different periodical batch flows

# Configuration

## Environment variables

| Name | Default value | Description | 
| --- | --- | --- |
| server.port | 8083 | A local bind port |
| spring.batch.job.restart.interval | 1 | An interval between the successful completion and the next launch of the certain batch job's execution |
| spring.batch.job.restart.timeUnit | 1 | An interval's time unit between the successful completion and the next launch of the certain batch job's execution |
| spring.batch.job.chunk.size | 8 | A size of the batch |
| spring.jms.broker.url | tcp://127.0.0.1:61616 | The host and the port of the JMS broker |
| spring.jms.broker.receive.timeout.interval | 10 | The message's receive timeout  |
| spring.jms.broker.receive.timeout.timeUnit | SECONDS | The message's receive timeout's time unit  |
| jdk.launcher.addmods | | Include into JVM arguments if run on JDK9: java.xml.bind,java.annotations.common |
| currencylayer.api.accesskey | | Currencylayer provider's accesskey |
| currencylayer.api.endpoint | | Currencylayer provider's URL |
    
# Bootstrap

## Local

Run using *Maven spring-boot plugin*:

`$mvn spring-boot:run`

## Cloudfoundry

`$ cf push -p target/manifest.yml`

     