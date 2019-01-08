package io.github.posseidon.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.TimeoutException;

@Slf4j
public class MQConnection implements AutoCloseable {

    private Channel channel;
    private Connection connection;

    public MQConnection(String uri){
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setUri(uri);
            factory.useNio();

            this.connection = factory.newConnection();
            this.channel = connection.createChannel();
        } catch (URISyntaxException | NoSuchAlgorithmException | KeyManagementException | IOException | TimeoutException e) {
            log.error("RabbitMQ Connection error", e);
            System.exit(2);
        }
    }

    public Channel channel() throws IOException {
        return this.connection.createChannel();
    }

    public void declareDirectExchange(String exchange) throws IOException {
        this.channel.exchangeDeclare(exchange, "direct", true);
        log.info("Exchange: [" + exchange + "] created.");
    }

    public void declareQueues(List<QDef> queues, String onExchange){
        queues.forEach(q -> {
            try {
                this.channel.queueDeclare(q.getQueue(), true, false, false, null);
                this.channel.queueBind(q.getQueue(), onExchange, q.getRoutingKey());
                log.info("Queue: [" + q.getQueue() + "] created and binned.");
            } catch (IOException e) {
                log.error("Failed to create Queue: " + q.getQueue(), e);
            }
        });
    }

    public <T extends DefaultConsumer> void consume(T consumer, String onQueue) throws IOException {
        consumer.getChannel().basicConsume(onQueue, consumer);
    }

    @Override
    public void close() throws IOException, TimeoutException {
        this.channel.close();
        this.connection.close();
    }
}
