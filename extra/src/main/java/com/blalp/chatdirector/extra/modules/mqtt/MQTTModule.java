package com.blalp.chatdirector.extra.modules.mqtt;

import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;

import com.blalp.chatdirector.core.ChatDirector;
import com.blalp.chatdirector.core.model.Context;
import com.blalp.chatdirector.core.model.IItem;
import com.blalp.chatdirector.core.model.IModule;

import org.fusesource.mqtt.client.Message;

public class MQTTModule implements IModule {

    @Override
    public boolean load() {
        if (ChatDirector.getConfig().getModuleData() == null
                || ChatDirector.getConfig().getModuleData().get("mqtt") == null) {
            if (ChatDirector.getConfig().hasDaemon(MQTTConnections.class)) {
                // Only spit out a warning if there were MQTT items
                ChatDirector.getLogger().log(Level.WARNING, "Failed to load MQTT module, no module_data");
            } else {
                // Of if debug mode is on
                //ChatDirector.getLogger().log(Level.INFO,"Failed to load MQTT module, no module_data. If you are not using MQTT items, you can safely ignore this.");
            }
            return true;
        }
        MQTTConnections connections = (MQTTConnections) ChatDirector.getConfig()
                .getOrCreateDaemon(MQTTConnections.class);
        for (Entry<String, String> connection : ChatDirector.getConfig().getModuleData().get("mqtt").entrySet()) {
            connections.put(connection.getKey(), new MQTTConnection(connection.getValue()));
        }
        return true;
    }

    @Override
    public boolean unload() {
        return true;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public List<String> getItemNames() {
        return Arrays.asList("mqtt-input", "mqtt-output");
    }

    @Override
    public Class<? extends IItem> getItemClass(String type) {
        switch (type) {
        case "mqtt-input":
            return MQTTInputItem.class;
        case "mqtt-output":
            return MQTTOutputItem.class;
        }
        return null;
    }

    @Override
    public Context getContext(Object object) {
        Context output = new Context();
        if(object instanceof Message){
            output.put("MQTT_TOPIC",((Message)object).getTopic());
            output.put("CURRENT",new String(((Message)object).getPayload()));
            output.put("MQTT_MESSAGE",output.getCurrent());
        }
        return output;
    }

}
