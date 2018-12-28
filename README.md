# Consul features:
- [x] Connecting to Consul server.
- [x] Load Configuration data from **key/value** store.
- [x] Auto-refresh on configuration data changes.
- [ ] Register application.
- [ ] Providing health checks endpoints.

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

## RabbitMQ features:
- [ ] Support for RabbitMQ connection.
- [ ] Interface for Queue Listeners.
- [ ] Interface for Queue Publishers.

