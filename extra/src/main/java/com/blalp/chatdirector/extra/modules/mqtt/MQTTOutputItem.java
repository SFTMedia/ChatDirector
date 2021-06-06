package com.blalp.chatdirector.extra.modules.mqtt;

import com.blalp.chatdirector.core.ChatDirector;
import com.blalp.chatdirector.core.model.Context;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import org.fusesource.mqtt.client.QoS;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class MQTTOutputItem extends MQTTItem {
    boolean async;
    String topic;

    @Override
    public Context process(Context context) {
        Context output = new Context();
        MQTTConnection connection = ((MQTTConnections) ChatDirector.getConfig()
                .getOrCreateDaemon(MQTTConnections.class)).get(this.connection);
        if (async) {
            connection.getFutureConnection().publish(topic, context.getCurrent().getBytes(), QoS.EXACTLY_ONCE, false);
        } else {
            try {
                connection.getBlockingConnection().publish(topic, context.getCurrent().getBytes(), QoS.EXACTLY_ONCE,
                        false);
            } catch (Exception e) {
                e.printStackTrace();
                ChatDirector.getLogger().warning("FAILED to send message " + this);
                if (!connection.blockingConnection.isConnected()) {
                    try {
                        ChatDirector.getLogger().warning("connection dropped, attempted re-connect...");
                        connection.blockingConnection.connect();
                        connection.getBlockingConnection().publish(topic, context.getCurrent().getBytes(),
                                QoS.EXACTLY_ONCE, false);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
        return output;
    }
}
