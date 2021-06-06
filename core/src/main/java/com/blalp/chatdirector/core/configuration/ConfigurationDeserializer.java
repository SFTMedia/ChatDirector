package com.blalp.chatdirector.core.configuration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.blalp.chatdirector.core.ChatDirector;
import com.blalp.chatdirector.core.model.IteratorIterable;
import com.blalp.chatdirector.core.model.Version;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class ConfigurationDeserializer extends JsonDeserializer<Configuration> {

    @Override
    public Configuration deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        ObjectCodec oc = p.getCodec();
        JsonNode node = oc.readTree(p);
        Configuration configuration = new Configuration();
        ChatDirector.getInstance().setConfigStaging(configuration);
        if (node.has("version")&&!node.get("version").asText().equals("0.2.5")) {
            configuration.setVersion(node.get("version").asText());
            LegacyConfiguration legacyConfiguration = new LegacyConfigurationDeserializer().deserialize(oc,node);
            LegacyConfiguration updatedConfiguration = legacyConfiguration.updateTo(new Version("0.2.5"));
            String updatedConfig = ChatDirector.getInstance().getObjectMapper().writeValueAsString(updatedConfiguration);
            if(legacyConfiguration.isDebug()){
                ChatDirector.getLogger().info("Updated config to");
                ChatDirector.getLogger().info(updatedConfig);
            }
            return ChatDirector.getInstance().getObjectMapper().readValue(updatedConfig, Configuration.class);
        }
        if (node.has("debug") && node.get("debug").isBoolean()) {
            configuration.setDebug(node.get("debug").asBoolean());
        }
        if (node.has("module_data")) {
            for (Entry<String, JsonNode> moduleData : new IteratorIterable<Entry<String, JsonNode>>(
                    node.get("module_data").fields())) {
                Map<String, String> readModuleData = new HashMap<>();
                for (Entry<String, JsonNode> singleModuleData : new IteratorIterable<>(
                        moduleData.getValue().fields())) {
                    readModuleData.put(singleModuleData.getKey(), singleModuleData.getValue().asText());
                }
                configuration.moduleData.put(moduleData.getKey(), readModuleData);
            }
        }
        for (Entry<String, JsonNode> chain : new IteratorIterable<>(node.get("chains").fields())) {
            Chain chainObj = chain.getValue().traverse(oc).readValueAs(Chain.class);
            configuration.chains.put(chain.getKey(), chainObj);
        }
        return configuration;
    }
}
