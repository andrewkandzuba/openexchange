**Config Service**
The microservice facilities central properties providing to others connected services 

Run with *Maven spring-boot plugin*
- with proxy: $mvn spring-boot:run -Drun.jvmArguments='-Dhttps.proxyHost=... -Dhttps.proxyPort=... -DCONFIG_GIT=... -DCONFIG_SERVICE_PASSWORD=...'
- w/o proxy: $ mvn spring-boot:run -Drun.jvmArguments='-DCONFIG_SERVICE_HOST=... -DCONFIG_SERVICE_PASSWORD=...'
