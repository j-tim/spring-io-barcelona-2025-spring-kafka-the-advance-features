Advisor Version: 1.7.2

```bash
advisor build-config get
```

```bash
advisor upgrade-plan get
```

## Build "custom" mappings

### Java Faker 

* https://mvnrepository.com/artifact/com.github.javafaker/javafaker
* ~/.m2/repository/com/github/javafaker/javafaker

```bash
advisor mapping create -c=com.github.javafaker:javafaker
```

### Apache Avro

* https://mvnrepository.com/artifact/org.apache.avro/avro
* ~/.m2/repository/org/apache/avro/avro

Add upgrade mappings for these dependencies:
        - com.github.javafaker:javafaker
        - org.apache.avro:avro
        - io.confluent:kafka-avro-serializer

```bash
advisor mapping create -c=org.apache.avro:avro
```

### Confluent kafka-avro-serializer

Maven settings: ~/.m2/settings.xml

```xml
<profiles>
    <profile>
        <id>confluent</id>
        <activation>
            <activeByDefault>true</activeByDefault>
        </activation>
        <repositories>
            <!-- Confluent Maven repo, required to download the Confluent Kafka Avro Serializer dependencies -->
            <repository>
                <id>confluent</id>
                <url>https://packages.confluent.io/maven/</url>
            </repository>
        </repositories>
    </profile>
</profiles>    
```

```bash
advisor mapping create -c=io.confluent:kafka-avro-serializer
```

## Example with custom mappings

```bash
export SPRING_ADVISOR_MAPPING_CUSTOM_0_FILEPATH=.advisor/mappings
```
```bash
advisor upgrade-plan get
```
Error:

```log
 Existing build-configuration is already up-to-date


🏃 [ 1 / 2 ] Validating syntax of upgrade mappings [00m 01s] ok
🏃 [ 2 / 2 ] Fetching and processing upgrade plan details [00m 01s] ok

Projects discovered:
        - javafaker: 1.0.x (no upgrades available)

No upgrade plan available.

⚠️  Warnings:
        - There is an error in jackson-annotations, several versions are used simultaneously:
                - com.fasterxml.jackson.core:jackson-annotations:2.17.2
                - com.fasterxml.jackson.core:jackson-annotations:2.18.3
        - There is an error in apache-commons-lang, several versions are used simultaneously:
                - org.apache.commons:commons-lang3:3.14.0
                - org.apache.commons:commons-lang3:3.17.0
        - There is an error in apache-commons-io, several versions are used simultaneously:
                - commons-io:commons-io:2.16.1
                - commons-io:commons-io:2.15.1
                - commons-io:commons-io:2.11.0
        - There is an error in dropwizard-metrics, several versions are used simultaneously:
                - com.yammer.metrics:metrics-core:2.2.0
                - io.dropwizard.metrics:metrics-core:4.1.12.1
        - There is an error in jackson, several versions are used simultaneously:
                - com.fasterxml.jackson.core:jackson-core:2.17.2
                - com.fasterxml.jackson.core:jackson-databind:2.17.2
                - com.fasterxml.jackson.core:jackson-core:2.18.3
                - com.fasterxml.jackson.core:jackson-databind:2.18.3
                - com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.18.3
                - com.fasterxml.jackson.datatype:jackson-datatype-jdk8:2.18.3
                - com.fasterxml.jackson.module:jackson-module-parameter-names:2.18.3
                - com.fasterxml.jackson.dataformat:jackson-dataformat-csv:2.18.3
                - com.fasterxml.jackson.module:jackson-module-scala_2.13:2.18.3
- There are no upgrade mappings configured for these transitive dependencies:
      - org.apache.kafka:kafka-storage-api
      - org.apache.kafka:kafka-streams
      - org.apache.kafka:kafka-server
      - commons-validator:commons-validator
      - org.apache.kafka:kafka-server-common
      - org.apache.kafka:kafka-metadata
      - org.apache.kafka:kafka-raft
      - org.apache.zookeeper:zookeeper
      - org.apache.kafka:kafka-streams-test-utils
      - org.apache.kafka:kafka_2.13
      - org.apache.kafka:kafka-storage
      - org.apache.kafka:kafka-group-coordinator
Please report these problems to support.broadcom.com
```

