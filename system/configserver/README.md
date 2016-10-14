# Overview

**Configuration Service**

The microservice facilities central properties providing to others connected services.
It also registers itself to the Eureka Service Registry to enable *Discovery First Bootstrap* to all clients.   

# Configuration

## Environment variables

| Name | Default value | Description | 
| --- | --- | --- |
| spring.cloud.config.server.git.uri | | GIT repository url |
| security.user.password | | Password of a global Configuration Service | 
| eureka.instance.metadataMap.user | | User of a global Configuration Service. To be registered into Service Registry. | 
| eureka.instance.metadataMap.password | | Password of a global Configuration Service. To be registered into Service Registry. | 
| eureka.instance.nonSecurePort | 8888 |  Local binding port |
| https.proxyHost | | HTTP proxy host if enabled |
| https.proxyPort | | HTTP proxy port if enabled |

# Bootstrap

## Local

Run using *Maven spring-boot plugin*:

`$mvn spring-boot:run -Drun.jvmArguments='...'`

Local run VM parameters:

`-server
 -ea
 --add-modules java.xml.bind,java.annotations.common
 -Dhttps.proxyHost=ee-mcagw1.ee.playtech.corp
 -Dhttps.proxyPort=8080
 -Dspring.cloud.config.server.git.uri=https://github.com/andrewkandzuba/openexchange-configuration.git
 -Dsecurity.user.password=changeit
 -Deureka.instance.nonSecurePort=8888
 -Deureka.instance.metadataMap.user=user
 -Deureka.instance.metadataMap.password=changeit`

## Cloudfoundry

`$ cf push -p target/manifest.yml`