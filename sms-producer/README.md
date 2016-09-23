# Overview

**Sms Production Service**

Test service to verify concurrency aspects of JMS/JPA combination from the consumption messages producing aspects

# Configuration

## Environment variables

| Name | Default value | Description | 
| --- | --- | --- |
| server.port | 8085 | A local bind port |
| jdk.launcher.addmods | | Include into JVM arguments if run on JDK9: java.xml.bind,java.annotations.common |
| sms.outbound.queue.write.chunk.size | 100 | The most number of messages produced ber a turn  |
| spring.jms.producer.concurrency | 1 | The number of parallel producers |

Properties inherited from **JMS Library**
    
# Bootstrap

## Local

Run using *Maven spring-boot plugin*:

`$mvn spring-boot:run`

## Cloudfoundry

`$ cf push -p target/manifest.yml`

     