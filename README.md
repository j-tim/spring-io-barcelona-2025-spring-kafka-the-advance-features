# Spring I/O Barcelona 2025 - Spring for Apache Kafka the advance features

[https://2025.springio.net/sessions/spring-for-apache-kafka-the-advanced-features/](https://2025.springio.net/sessions/spring-for-apache-kafka-the-advanced-features/)

Demo codebase for our Spring I/O 2025 Barcelona talk

## Overview of applications

| Application Maven Module                      | Port | Tracing | Metrics | Logging | Consumer groups                                                                       | Topics                                                                                                   | Description                                             |
|-----------------------------------------------|------|---------|---------|---------|---------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------|---------------------------------------------------------|
| spring-kafka-producer                         | 8080 | Y       | Y       | Y       | n.a.                                                                                  | Producer: `stock-quotes-avro`, `stock-quotes-avro-second` and `stock-quotes-json`                        | Multiple producers (Json and Avro)                      |
| spring-kafka-multiple-consumers-beans         | 8083 | Y       | Y       | Y       | `multiple-consumer-beans-group-avro` and `multiple-consumer-beans-group-json`         | Consumes: `stock-quotes-avro` and `stock-quotes-json`                                                    | Multiple consumers, using Spring Kafka beans            | 
| spring-kafka-multiple-consumers-annotations   | 8082 | Y       | Y       | Y       | `vertical-scaling-consumer-group-avro` and `non-vertical-scaling-consumer-group-json` | Consumes: `stock-quotes-avro` and `stock-quotes-json`                                                    | Multiple consumers, using Spring Kafka Annotations      |
| spring-kafka-multiple-consumers-configuration | 8084 | Y       | Y       | Y       | `custom-kafka-consumer-group-avro` and `non-vertical-scaling-consumer-group-json`     | Consumes: `stock-quotes-avro`, `stock-quotes-avro-second` and `stock-quotes-json`                        | Multiple consumers, using custom code and configuration |
| non-blocking-retries-example                  | 8085 | Y       | Y       | Y       | `non-blocking-retries-consumer-group`                                                 | Consumes: `events`, `events-retry-topic-1000`, `events-retry-topic-2000`, `events-retry-topic-4000` and `events-retry-topic-dlt` | Non blocking retries example                            |
| blocking-retries-example                      | 8086 | Y       | Y       | Y       | `blocking-retries-consumer-group`                                                     | Consumes: `events`                                                                                       | Blocking retries example                                |

## Build the project

```shell
./mvnw clean install
```

## Running the infrastructure + Monitoring

* Confluent Platform (Kafka + Schema Registry): 7.8.2
* Conduktor Console: 1.33.0
* Monitoring: Grafana, Prometheus, Loki and Tempo

```shell
docker-compose up -d
```

```shell
docker compose down -v
```

## Demos

### Demo 1

1. Spring Kafka Consumer config with Beans
2. Spring Kafka Consumer using Annotations
3. Consumers as configuration

### Demo 2

* Spring Kafka Consumer vertical scaling

### Demo 3

1. Blocking vs Non-blocking retries
2. Observability Demo