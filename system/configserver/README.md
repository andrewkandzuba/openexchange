# Central Configuration Service

The centralized configuration service on *Spring Cloud Config Server* framework.
The service must register itself to the **_Eureka Service Registry Service_** to enable *Discovery First Bootstrap* to all clients.   

## Properties 

| Name | Default value | Description | 
| --- | :---: | --- |
| spring.cloud.config.server.git.uri | | GIT repository url |
| security.user.password | | Password of a global Configuration Service | 
| eureka.instance.nonSecurePort | 8888 |  Local binding port |
| eureka.instance.metadataMap.user | | User of a global Configuration Service. To be registered into Service Registry. | 
| eureka.instance.metadataMap.password | | Password of a global Configuration Service. To be registered into Service Registry. | 