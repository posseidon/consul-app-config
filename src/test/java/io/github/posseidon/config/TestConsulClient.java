package io.github.posseidon.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class TestConsulClient {

    @Test
    void fromUrl() {
        ConsulClient consulClient = ConsulClient.fromUrl(null);
        Assertions.assertTrue(consulClient.isAlive());
    }

    @Data
    @NoArgsConstructor
    static class TestProperties extends  Properties {
    }

    @Test
    void loadKV(){
        ConsulClient consulClient = ConsulClient.fromUrl(null);
        try {
            TestProperties properties = consulClient.properties(TestProperties.class);
            Assertions.assertEquals("name_of_rabbitmq_exchange", properties.exchange);
        } catch (IOException e) {
            Assertions.fail(e);
        }
    }

    @Data
    @NoArgsConstructor
    static class AnotherTestProperties extends  Properties {
        private String sampleProperty;
    }

    @Test
    void loadComplexProp(){
        ConsulClient consulClient = ConsulClient.fromUrlKv(null, null);
        try {
            AnotherTestProperties anotherTestProperties = consulClient.properties(AnotherTestProperties.class);
            Assertions.assertEquals("I'm a sample property.", anotherTestProperties.sampleProperty);
        } catch (IOException e) {
            Assertions.fail(e);
        }
    }

    @Test
    void run() throws InterruptedException {
        ConsulClient consulClient = ConsulClient.fromUrl(null);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(consulClient);

        executorService.awaitTermination(1, TimeUnit.SECONDS);
        executorService.shutdown();
    }
}