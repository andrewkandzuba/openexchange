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
 -Dspring.cloud.config.label=...
 -Dvcap.services.eureka-service.credentials.uri=...
 -Dspring.rabbitmq.addresses=amqp://ymqcbabg:FNie0JFq6O5zUlKKtT7qG-HkRyT7XHCO@wildboar.rmq.cloudamqp.com/ymqcbabg
 -Dspring.datasource.url=jdbc:mysql://us-cdbr-iron-east-04.cleardb.net/ad_79bf6586c0d1648
 -Dspring.datasource.username=bed9dd9d6f797f
 -Dspring.datasource.password=b7e5b5b4
 -Dspring.datasource.tomcat.max-active=2
 -Dspring.activemq.broker-url=tcp://localhost:61616
 -Dorg.apache.activemq.SERIALIZABLE_PACKAGES="*"`

## Cloudfoundry

`$ cf push -p target/manifest.yml`

     