package hu.intersoft.loca.config;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class AppConfig<T extends Properties> {
    private T properties;

    public AppConfig(Class<T> type) {
        try {
            properties = ConsulClient.fromUrl(System.getenv("CONSUL_URL")).properties(System.getenv("CONSUL_KV"), type);
        } catch (IOException e) {
            log.error("Inialization of AppConfig error", e);
        }
    }

    public T getProperties() {
        return this.properties;
    }
}
