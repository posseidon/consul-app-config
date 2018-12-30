package io.github.posseidon.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public abstract class AppConfig<T extends Properties> implements PropertyChangeListener {
    private ObjectMapper objectMapper = new ObjectMapper();
    private Class<T> type;
    protected T properties;

    public AppConfig(Class<T> type) {
        try {
            this.type = type;

            ConsulClient consulClient = ConsulClient.fromUrlKv(System.getenv("CONSUL_URL"), System.getenv("CONSUL_KV"));
            consulClient.addPropertyChangeListener(this);

            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.submit(consulClient);

            properties = consulClient.properties(type);
        } catch (IOException e) {
            log.error("Inialization of AppConfig error", e);
        }
    }

    public T getProperties() {
        return this.properties;
    }

    public void propertyChange(PropertyChangeEvent evt) {
        try {
            this.properties = objectMapper.readValue((byte[]) evt.getNewValue(), type);
            log.info(objectMapper.writeValueAsString(this.properties));
        } catch (IOException e) {
            log.error("Parse Error.", e);
        }
    }
}
