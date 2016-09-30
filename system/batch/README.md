# Overview

**Batch scheduled jobs library**

The library discovers all visible methods annotated with @Job in @Configuration classes loaded into ApplicationContext and then try to schedule a repeatable tasks.

# Configuration

## Environment variables

| Name | Default value | Description | 
| --- | --- | --- |
| spring.batch.job.restart.interval | 100000 | An interval between the successful completion and the next launch of the certain batch job's execution |
| spring.batch.job.restart.timeunit | MINUTES | An interval time units |

     