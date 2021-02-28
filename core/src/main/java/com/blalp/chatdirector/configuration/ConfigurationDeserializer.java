package com.blalp.chatdirector.configuration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.blalp.chatdirector.model.IteratorIterable;
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
        if (node.has("debug") && node.get("debug").isBoolean()) {
            configuration.setDebug(node.get("debug").asBoolean());
        }
        if (node.has("module_data")) {
            for (Entry<String, JsonNode> moduleData : new IteratorIterable<Entry<String, JsonNode>>(node.get("module_data").fields())) {
                Map<String, String> readModuleData = new HashMap<>();
                for(JsonNode moduleArrayItem : new IteratorIterable<JsonNode>(moduleData.getValue().elements())) {
                    for (Entry<String, JsonNode> singleModuleData : new IteratorIterable<>(moduleArrayItem.fields())) {
                        readModuleData.put(singleModuleData.getKey(), singleModuleData.getValue().asText());
                    }
                }
                configuration.moduleData.put(moduleData.getKey(), readModuleData);
            }
        }
        // System.out.println(ChatDirector.getConfig().getModules());
        Chain chainObj;
        for (JsonNode chain : new IteratorIterable<JsonNode>(node.get("chains").elements())) {
            chainObj = null;
            for (Entry<String, JsonNode> innerChain : new IteratorIterable<>(chain.fields())) {
                if (chainObj != null) {
                    System.err.println("More than one chain in a chain?");
                    break;
                }
                chainObj = innerChain.getValue().traverse(oc).readValueAs(Chain.class);
                configuration.chains.put(innerChain.getKey(), chainObj);
            }
        }
        return configuration;
    }
}
