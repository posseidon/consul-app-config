package hu.intersoft.loca.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orbitz.consul.Consul;
import com.orbitz.consul.ConsulException;
import com.orbitz.consul.model.kv.Value;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Optional;

@Slf4j
class ConsulClient {

    private static ObjectMapper objectMapper = new ObjectMapper();
    private final Consul consul;

    private ConsulClient(String url){
        String consulHostString = Optional.ofNullable(url).orElse("http://localhost:8500");
        consul = Consul.builder().withUrl(consulHostString).build();
    }

    static ConsulClient fromUrl(String url){
        return new ConsulClient(url);
    }

    boolean isAlive(){
        String leader = consul.statusClient().getLeader();
        log.info("Consul leader: {}", leader);
        return !leader.isEmpty();
    }

    <T extends Properties> T properties(String kv, Class<T> type) throws IOException {
        String confKey = Optional.ofNullable(kv).orElse("config/demo");
        Value configValue = consul.keyValueClient().getValue(confKey).orElseThrow(() -> new ConsulException("Configuration not found: " + kv));
        return objectMapper.readValue(configValue.getValueAsBytes().get(), type);
    }
}
