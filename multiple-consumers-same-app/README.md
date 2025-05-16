# Spring I/O Barcelona 2025 - Run multiple consumers concurrently in the same application context, using spring boot features

## In this demo we showcase

* How we can run multiple consumer instances with the same configurations in parallel
* How we can configure multiple consumer listener factories within the same application

## Running the demo

### Running the infrastructure

```shell
docker-compose up -d
```

### Running the producer

```shell
cd spring-kafka-producer
mvn spring-boot:run
```

### Running the consumer

```shell
cd multiple-consumers-same-app/spring-kafka-multiple-consumers-configuration
mvn spring-boot:run
```

### Shutdown

```shell
docker-compose down -v
```

