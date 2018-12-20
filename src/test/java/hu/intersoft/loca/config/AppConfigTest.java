package hu.intersoft.loca.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static java.lang.System.getProperties;
import static org.junit.jupiter.api.Assertions.*;

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