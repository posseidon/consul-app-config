package io.github.posseidon.mq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class QDef {
    
    private String queue, routingKey;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QDef qDef = (QDef) o;
        return queue.equals(qDef.queue) &&
                routingKey.equals(qDef.routingKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(queue, routingKey);
    }

    @Override
    public String toString() {
        return "QDef{" +
                "queue='" + queue + '\'' +
                ", routingKey='" + routingKey + '\'' +
                '}';
    }
}
