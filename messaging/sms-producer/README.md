# Overview

**Sms Production Service**

Test service to verify concurrency aspects of JMS/JPA combination from the consumption messages producing aspects

# Configuration

## Environment variables

| Name | Default value | Description | 
| --- | --- | --- |
| server.port | 8085 | A local bind port |
| sms.outbound.queue.write.chunk.size | 100 | The most number of messages produced ber a turn  |
| spring.producer.concurrency | 1 | The number of parallel producers |

Properties inherited from either:
- **activemq-sms-transport**
- **rabbitmq-sms-transport**
    
# Bootstrap

## Local

Run using *Maven spring-boot plugin*:

`$mvn spring-boot:run`

Local run VM parameters:

`-server
  -ea
  --add-modules
  java.xml.bind,java.annotations.common
  -Dspring.cloud.config.label=...
  -Dvcap.services.eureka-service.credentials.uri=...
  -Dspring.rabbitmq.addresses=amqp://ymqcbabg:FNie0JFq6O5zUlKKtT7qG-HkRyT7XHCO@wildboar.rmq.cloudamqp.com/ymqcbabg
  -Dspring.activemq.broker-url=tcp://localhost:61616
  -Dorg.apache.activemq.SERIALIZABLE_PACKAGES="*"`
  


## Cloudfoundry

`$ cf push -p target/manifest.yml`

     