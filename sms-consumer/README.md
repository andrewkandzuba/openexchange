# Overview

**Sms Consumption Service**

Test service to verify concurrency aspects of JMS/JPA combination from the consumption aspects

# Configuration

## Environment variables

| Name | Default value | Description | 
| --- | --- | --- |
| server.port | 8084 | A local bind port |
| jdk.launcher.addmods | | Include into JVM arguments if run on JDK9: java.xml.bind,java.annotations.common |
| sms.outbound.queue.read.chunk.size | 100 | The most number of messages consumed ber a turn  |
| spring.jms.consumers.concurrency | The number of available processor's cores | The number of parallel consumers |

Properties inherited from **JMS Library**
    
# Bootstrap

## Local

Run using *Maven spring-boot plugin*:

`$mvn spring-boot:run`

## Cloudfoundry

`$ cf push -p target/manifest.yml`

     