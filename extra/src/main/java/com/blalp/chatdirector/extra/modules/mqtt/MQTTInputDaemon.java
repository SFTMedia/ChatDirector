package com.blalp.chatdirector.extra.modules.mqtt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.blalp.chatdirector.core.ChatDirector;
import com.blalp.chatdirector.core.model.IDaemon;
import com.blalp.chatdirector.core.model.IItem;

import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;

public class MQTTInputDaemon implements IDaemon {

    List<MQTTInputItem> pendingItems = new ArrayList<>();
    HashMap<String, HashMap<String, List<MQTTInputItem>>> items = new HashMap<>();
    List<Thread> workers = new ArrayList<>();

    @Override
    public boolean load() {
        MQTTConnections mqttConnections = (MQTTConnections) ChatDirector.getConfig()
                .getOrCreateDaemon(MQTTConnections.class);
        if(!mqttConnections.load()){
            ChatDirector.getLogger().warning("MQTT connections failed.");
            return false;
        }
        for (MQTTInputItem item : pendingItems) {
            if (!items.containsKey((item).connection)) {
                items.put((item).connection, new HashMap<>());
            }
            if (!items.get((item).connection).containsKey((item).topic)) {
                items.get((item).connection).put((item).topic, new ArrayList<>());
            }
            List<MQTTInputItem> existing = items.get((item).connection)
                    .get((item).topic);
            existing.add(item);
        }
        for (Entry<String, HashMap<String, List<MQTTInputItem>>> itemConnection : items.entrySet()) {
            if (!mqttConnections.containsKey(itemConnection.getKey())) {
                ChatDirector.getLogger()
                        .warning("MQTT Connection " + itemConnection.getKey() + " does not exist in module_data");
                return false;
            }
            MQTTConnection connection = mqttConnections.get(itemConnection.getKey());
            for (Entry<String, List<MQTTInputItem>> itemTopic : itemConnection.getValue().entrySet()) {
                byte[] resp;
                try {
                    resp = connection.futureConnection
                            .subscribe(new Topic[] { new Topic(itemTopic.getKey(), QoS.EXACTLY_ONCE) }).await();
                    if (ChatDirector.getConfig().isDebug()) {
                        ChatDirector.getLogger().info("Subscribed to MQTT with topic " + itemTopic.getKey()
                                + " on connection " + itemConnection.getKey() + " with responce" + resp);
                    }
                } catch (Exception e) {
                    ChatDirector.getLogger().warning("FAILED to subscribe to MQTT with topic " + itemTopic.getKey()
                            + " on connection " + itemConnection.getKey());
                    e.printStackTrace();
                    return false;
                }
            }
            Thread thread = new Thread(
                    new MQTTInputDaemonWorker(connection, itemConnection.getKey(), itemConnection.getValue()));
            workers.add(thread);
            thread.run();
        }
        return true;
    }

    @Override
    public boolean unload() {
        return true;
    }

    @Override
    public void addItem(IItem item) {
        if (item instanceof MQTTInputItem) {
            pendingItems.add((MQTTInputItem) item);
        }
    }
}
