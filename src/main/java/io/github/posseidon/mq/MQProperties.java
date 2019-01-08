package io.github.posseidon.mq;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class MQProperties {
    protected String amqp, exchange;
}
