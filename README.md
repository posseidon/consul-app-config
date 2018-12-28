# Consul application configuration loader

## Consul features:
- [x] Connecting to Consul server.
- [x] Load Configuration data from **key/value** store.
- [x] Auto-refresh on configuration data changes.
- [ ] Register application.
- [ ] Providing health checks endpoints.

## RabbitMQ features:
- [ ] Support for RabbitMQ connection.
- [ ] Interface for Queue Listeners.
- [ ] Interface for Queue Publishers.

## Usage

Environment variables:
- CONSUL_URL
- CONSUL_KV - absolute path key/value configuration settings.

If not provided, library is connecting to 
- ``http://localhost:8500`` and 
- looking for ``config/demo`` configuration.


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
defined in ``Properties`` abstract class.

Example using AppConfig:


```java
class TestAppConfig {

    @EqualsAndHashCode(callSuper = true)
    @Data
    @NoArgsConstructor
    static class AnotherTestProperties extends  Properties {
        private String sampleProperty;
    }

    static class MyAppConfig extends AppConfig<AnotherTestProperties> {
        MyAppConfig(){
            super(AnotherTestProperties.class);
        }

        String exchange(){
            return properties.exchange;
        }
    }

    @Test
    void getInstance() {
        MyAppConfig appConfig = new MyAppConfig();

        AnotherTestProperties anotherTestProperties = appConfig.getProperties();
        Assertions.assertEquals("I'm a sample property.", anotherTestProperties.sampleProperty);

        String exchange = appConfig.exchange();
        Assertions.assertEquals("name_of_rabbitmq_exchange", exchange);
    }
}
```

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

## Testing environment

Using Consul docker container to test the library, run
```jshelllanguage
$ docker-compose up -d
```

Source [docker-compose.yml](docs/consul/docker-compose.yml)