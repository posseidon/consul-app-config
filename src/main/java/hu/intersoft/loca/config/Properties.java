package hu.intersoft.loca.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class Properties {
    String amqp, exchange;
    QDef listenOn;

    public String getAmqp() {
        return amqp;
    }

    public void setAmqp(String amqp) {
        this.amqp = amqp;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public QDef getListenOn() {
        return listenOn;
    }

    public void setListenOn(QDef listenOn) {
        this.listenOn = listenOn;
    }
}
