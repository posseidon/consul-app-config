# Consul application configuration loader

## Features:
1. Connecting to Consul server.
2. Load Configuration data from **key/value** store.
3. Auto-refresh on configuration data changes.
4. Optionally provides support for RabbitMQ connection.

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
class AppConfigTest {

    @EqualsAndHashCode(callSuper = true)
    @Data
    @NoArgsConstructor
    static class AnotherTestProperties extends  Properties {
        private String sampleProperty;
    }

    static class TestAppConfig extends AppConfig<AnotherTestProperties> {
        TestAppConfig(){
            super(AnotherTestProperties.class);
        }

        
        String exchange(){
            return properties.exchange;
        }
    }

    @Test
    void getInstance() {
        TestAppConfig appConfig = new TestAppConfig();
        
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
