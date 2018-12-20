# Consul application configuration loader

## Features:
1. Connecting to Consul server.
2. Load Configuration data from **key/value** store.
3. Optionally provides support for RabbitMQ connection.
4. Optionally auto-refresh on configuration data changes.

### Dependencies
Consul dependency:
```xml
<dependency>
    <groupId>com.orbitz.consul</groupId>
    <artifactId>consul-client</artifactId>
    <version>1.2.7</version>
</dependency>
```

RabbitMQ dependency:
```xml
<dependency>
    <groupId>com.rabbitmq</groupId>
    <artifactId>amqp-client</artifactId>
    <version>5.5.1</version>
</dependency>
```

Lombok dependency:
```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.4</version>
    <scope>provided</scope>
</dependency>
```

Jackson dependency:
```xml
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.9.7</version>
</dependency>
```

## Usage

Environment variables:
- CONSUL_URL
- CONSUL_KV - absolute key/value path.

If not provided, library is connecting to ``http://localhost:8500`` and looking for
``config/demo`` configuration.



Required configuration values:
```json
{
  "amqp": "amqp://username:password@hostname:port/virtualhost",
  "exchange": "name_of_rabbitmq_exchange",
  "listenOn": {
    "queue": "name_of_queue",
    "routingKey": "routing_key_of_queue"
  }
}
```



```java
class AppConfigTest {

    @Data
    @NoArgsConstructor
    static class AnotherTestProperties extends  Properties {
        private String sampleProperty;
    }

    @Test
    void getInstance() {
        AppConfig<AnotherTestProperties> appConfig = new AppConfig<>(AnotherTestProperties.class);
        AnotherTestProperties anotherTestProperties = appConfig.getProperties();
        Assertions.assertEquals("name_of_rabbitmq_exchange", anotherTestProperties.exchange);
        Assertions.assertEquals("I'm a sample property.", anotherTestProperties.sampleProperty);
    }
}
```