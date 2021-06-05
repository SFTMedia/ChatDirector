package com.blalp.chatdirector.core.configuration;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.blalp.chatdirector.core.model.IteratorIterable;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.JsonNode;


public class LegacyConfigurationDeserializer extends JsonDeserializer<LegacyConfiguration> {

    @Override
    public LegacyConfiguration deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        ObjectCodec oc = p.getCodec();
        JsonNode node = oc.readTree(p);
        LegacyConfiguration configuration = new LegacyConfiguration();
        
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
        LegacyChain chainObj;
        for (JsonNode chain : new IteratorIterable<JsonNode>(node.get("chains").elements())) {
            chainObj = null;
            for (Entry<String, JsonNode> innerChain : new IteratorIterable<>(chain.fields())) {
                if (chainObj != null) {
                    System.err.println("More than one chain in a chain?");
                    break;
                }
                chainObj = innerChain.getValue().traverse(oc).readValueAs(LegacyChain.class);
                configuration.chains.put(innerChain.getKey(), chainObj);
            }
        }
        return configuration;
    }
}
