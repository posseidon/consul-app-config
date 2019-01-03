package io.github.posseidon.config;

import io.github.posseidon.mq.MQProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestAppConfig {

    @EqualsAndHashCode(callSuper = true)
    @Data
    @NoArgsConstructor
    static class AnotherTestMQProperties extends MQProperties {
        private String sampleProperty;
    }

    static class MyAppConfig extends AppConfig<AnotherTestMQProperties> {
        MyAppConfig(){
            super(AnotherTestMQProperties.class);
        }

        String exchange(){
            return properties.getExchange();
        }
    }

    @Test
    void getInstance() {
        MyAppConfig appConfig = new MyAppConfig();

        AnotherTestMQProperties anotherTestProperties = appConfig.getProperties();
        Assertions.assertEquals("I'm a sample property.", anotherTestProperties.sampleProperty);

        String exchange = appConfig.exchange();
        Assertions.assertEquals("name_of_rabbitmq_exchange", exchange);
    }
}
