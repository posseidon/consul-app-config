package hu.intersoft.loca.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
