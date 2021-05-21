package com.blalp.chatdirector.extra.modules.mqtt;

import java.util.HashMap;
import java.util.List;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Context;

import org.fusesource.mqtt.client.Message;

public class MQTTInputItemDaemonWorker implements Runnable {

    String connectionName;
    MQTTConnection connection;
    HashMap<String, List<MQTTInputItem>> items;

    public MQTTInputItemDaemonWorker(MQTTConnection connection, String connectionName,
            HashMap<String, List<MQTTInputItem>> items) {
        this.connection = connection;
        this.connectionName = connectionName;
        this.items = items;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Message message = connection.futureConnection.receive().await();
                message.ack();
                Context context = ChatDirector.getInstance().getModule(MQTTModule.class).getContext(message);
                for (MQTTInputItem item : items.get(message.getTopic())) {
                    ChatDirector.run(item, context, true);
                }
            } catch (Exception e) {
                ChatDirector.getLogger().warning("FAILED to get message from MQTT connection " + connectionName);
                e.printStackTrace();
            }
        }
    }

}
