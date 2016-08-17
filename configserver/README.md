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
| ureka.instance.metadataMap.user | | User of a global Configuration Service. To be registered into Service Registry. | 
| ureka.instance.metadataMap.password | | Password of a global Configuration Service. To be registered into Service Registry. | 
| eureka.instance.nonSecurePort | 8888 |  Local binding port |
| https.proxyHost | | HTTP proxy host if enabled |
| https.proxyPort | | HTTP proxy port if enabled |
| jdk.launcher.addmods | | Include into JVM arguments if run on JDK9: java.xml.bind,java.annotations.common |

# Bootstrap

## Local

Run using *Maven spring-boot plugin*:

`$mvn spring-boot:run -Drun.jvmArguments='...'`

## Cloudfoundry

`$ cf push -p target/manifest.yml`