# Overview

**Configuration Service**

The microservice facilities central properties providing to others connected services.
It also registers itself to the Eureka Service Registry to enable *Discovery First Bootstrap* to all clients.   

# Configuration

## Environment variables

| Name | Default value | Description | 
| --- | --- | --- |
| CONFIG_SERVER_GIT | | GIT repository url |
| CONFIG_SERVER_PASSWORD | | A password of global Configuration Service | 
| EUREKA_SERVER_URL | | URL of Eureka Service Registry |
| LOCAL_INSTANCE_PORT | 80 | The local instance's port to be reported to the service registry  |
| server.port | 8888 |  A local bind port |
| https.proxyHost | | A HTTP proxy host if enabled |
| https.proxyPort | | A HTTP proxy port if enabled |

# Bootstrap

## Local

Run using *Maven spring-boot plugin*:

`$mvn spring-boot:run -Drun.jvmArguments='-Dhttps.proxyHost=... -Dhttps.proxyPort=... -DCONFIG_SERVER_GIT=... -DCONFIG_SERVER_PASSWORD=...'`
