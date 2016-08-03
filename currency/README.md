**Currency Service** 
The microservice facilities currencies details and exchange rates statistics. 

| Method | Path | Description | User authenticated | Available from UI |
| --- | :--- | --- | :---: | :---: |
| GET | /currencies/ | Lists all registered currencies | | × |
| GET | /rates/current/{from}/{to} | Returns instant currencies exchange rate |  | × |
| GET | /rates/current/{date}/{from}/{to} | Returns currencies exchange rate at given data |  | × |

**Config Service**
The microservice facilities central properties providing to others connected services 

Run with *Maven spring-boot plugin*
- $ mvn spring-boot:run -Drun.jvmArguments='-DCONFIG_SERVICE_HOST=... -DCONFIG_SERVICE_PORT=... -DCONFIG_SERVICE_PASSWORD=...'

