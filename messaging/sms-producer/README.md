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
| spring.producer.concurrency | 1 | The number of parallel producers |

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
 -Dhttps.proxyHost=ee-mcagw1.ee.playtech.corp
 -Dhttps.proxyPort=8080
 -Dspring.cloud.config.server.git.uri=https://github.com/andrewkandzuba/openexchange-configuration.git
 -Dsecurity.user.password=changeit
 -Deureka.instance.nonSecurePort=8888
 -Deureka.instance.metadataMap.user=user
 -Deureka.instance.metadataMap.password=changeit
 -Dspring.cloud.config.label=1.0.5-SNAPSHOT
 -Dspring.activemq.broker-url=tcp://localhost:61616
 -Dspring.jobs.jobs.restart.timeunit=SECONDS
 -Dspring.jobs.jobs.restart.interval=10
 -Dspring.cloud.config.refresh.enabled=false`

## Cloudfoundry

`$ cf push -p target/manifest.yml`

     