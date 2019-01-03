package io.github.posseidon.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.posseidon.mq.MQProperties;
import io.github.posseidon.mq.QDef;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

class TestMQProperties {
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static String exchange = "name_of_rabbitmq_exchange";
    private static QDef listenOn = new QDef("name_of_queue", "routing_key_of_queue");

    static class TestProp extends MQProperties {
    }

    @Test
    void deserialize() throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get("src/test/resources/properties.json"));
        TestProp testProperties = objectMapper.readValue(bytes, TestProp.class);
        Assertions.assertEquals(exchange, testProperties.getExchange());
        Assertions.assertEquals(listenOn, testProperties.getListenOn());
    }
}