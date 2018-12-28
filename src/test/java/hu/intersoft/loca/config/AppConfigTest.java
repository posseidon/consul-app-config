package hu.intersoft.loca.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static java.lang.System.getProperties;
import static org.junit.jupiter.api.Assertions.*;

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
