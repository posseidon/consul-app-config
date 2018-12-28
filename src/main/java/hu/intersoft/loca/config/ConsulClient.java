package hu.intersoft.loca.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orbitz.consul.Consul;
import com.orbitz.consul.ConsulException;
import com.orbitz.consul.KeyValueClient;
import com.orbitz.consul.cache.KVCache;
import com.orbitz.consul.model.kv.Value;
import lombok.extern.slf4j.Slf4j;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.Optional;

@Slf4j
class ConsulClient implements Runnable, AutoCloseable{

    private PropertyChangeSupport support;
    private static ObjectMapper objectMapper = new ObjectMapper();
    private final Consul consul;
    private final KeyValueClient keyValueClient;
    private String kv;
    private KVCache kvCache;

    private ConsulClient(String url, String kv){
        support = new PropertyChangeSupport(this);
        String consulHostString = Optional.ofNullable(url).orElse("http://localhost:8500");
        this.kv = Optional.ofNullable(kv).orElse("config/demo");
        consul = Consul.builder().withUrl(consulHostString).build();
        this.keyValueClient = consul.keyValueClient();
    }

    static ConsulClient fromUrl(String url){
        return new ConsulClient(url, null);
    }

    static ConsulClient fromUrlKv(String url, String kv){
        return new ConsulClient(url, kv);
    }

    void addPropertyChangeListener(PropertyChangeListener pcl){
        support.addPropertyChangeListener(pcl);
    }

    boolean isAlive(){
        String leader = consul.statusClient().getLeader();
        log.info("Consul leader: {}", leader);
        return !leader.isEmpty();
    }

    <T extends Properties> T properties(Class<T> type) throws IOException {
        Value configValue = this.keyValueClient.getValue(this.kv).orElseThrow(() -> new ConsulException("Configuration not found: " + kv));
        return objectMapper.readValue(configValue.getValueAsBytes().get(), type);
    }

    @Override
    public void run() {
        this.kvCache = KVCache.newCache(this.keyValueClient, this.kv);
        this.kvCache.addListener(newValues -> {
            Optional<Value> newValue = newValues.values().stream()
                    .filter(value -> value.getKey().equals(this.kv))
                    .findAny();

            newValue.ifPresent(value -> support.firePropertyChange("properties", null, value.getValueAsBytes().get()));
        });
        this.kvCache.start();
    }

    @Override
    public void close() {
        this.kvCache.stop();
        this.consul.destroy();
    }
}
