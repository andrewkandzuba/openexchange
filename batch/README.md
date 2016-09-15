# Overview

**Batch Service Library**

The library retrieves all instances of org.springframework.batch.core.Job and tries to rerun them based on the predefined fixed rate 

# Configuration

## Environment variables

| Name | Default value | Description | 
| --- | --- | --- |
| spring.batch.job.restart.interval.milliseconds | 600000 | An interval between the successful completion and the next launch of the certain batch job's execution in milliseconds |
    
# Bootstrap

## Local

Run using *Maven spring-boot plugin*:

`$mvn spring-boot:run`

## Cloudfoundry

`$ cf push -p target/manifest.yml`

     