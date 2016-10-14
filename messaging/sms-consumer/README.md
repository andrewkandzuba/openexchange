# Overview

**Sms Consumption Service**

Test service to verify concurrency aspects of JMS/JPA combination from the consumption aspects

# Configuration

## Environment variables

| Name | Default value | Description | 
| --- | --- | --- |
| server.port | 8084 | A local bind port |
| sms.outbound.queue.read.chunk.size | 100 | The most number of messages consumed ber a turn  |
| spring.consumers.concurrency | The number of available processor's cores | The number of parallel consumers |

Properties inherited from **JMS Library**
    
# Bootstrap

## Local

Run using *Maven spring-boot plugin*:

`$mvn spring-boot:run`

Local run VM parameters:

`-server
 -ea
 --add-modules
 java.xml.bind,java.annotations.common
 -Deureka.instance.nonSecurePort=8888
 -Deureka.instance.metadataMap.user=user
 -Deureka.instance.metadataMap.password=changeit
 -Dspring.cloud.config.label=1.0.5-SNAPSHOT
 -Dspring.cloud.config.refresh.enabled=false
 -Dspring.jobs.jobs.restart.timeunit=SECONDS
 -Dspring.jobs.jobs.restart.interval=10
 -Dspring.rabbitmq.addresses=amqp://localhost:5672
 -Dspring.activemq.broker-url=tcp://localhost:61616
 -Dorg.apache.activemq.SERIALIZABLE_PACKAGES="*"`
 

## Cloudfoundry

`$ cf push -p target/manifest.yml`

     