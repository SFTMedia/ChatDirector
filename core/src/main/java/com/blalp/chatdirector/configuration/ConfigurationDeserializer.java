package com.blalp.chatdirector.configuration;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map.Entry;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.IteratorIterable;
import com.blalp.chatdirector.model.IModule;
import com.blalp.chatdirector.modules.common.CommonModule;
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
        // Set them the same as the existing ones so both arrays get populates
        // simultaneously.
        // So the existing singletons can still work during construction of the new
        // Configuration.
        configuration.modules = ChatDirector.config.getModules();
        configuration.modules.clear();
        configuration.chains = ChatDirector.config.getChains();
        configuration.chains.clear();

        configuration.modules.add(new CommonModule());
        for (JsonNode module : new IteratorIterable<JsonNode>(node.get("modules").elements())) {
            String moduleType = null;
            JsonNode moduleValue = null;
            if (module.isTextual()) {
                moduleType = module.asText();
                moduleValue = null;
            } else if (module.isContainerNode()) {
                for (Entry<String, JsonNode> inner : new IteratorIterable<>(module.fields())) {
                    moduleType = inner.getKey();
                    moduleValue = inner.getValue();
                    break;
                }
            } else {
                System.err.println(" Invalid module " + module);
                return null;
            }
            Class<?> moduleClass = ChatDirector.instance.getModuleClass(moduleType);
            if (moduleClass != null && IModule.class.isAssignableFrom(moduleClass)) {
                IModule moduleObj = null;
                if (moduleValue == null) {
                    try {
                        moduleObj = (IModule) moduleClass.getConstructors()[0].newInstance();
                    } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                            | InvocationTargetException | SecurityException e) {
                        e.printStackTrace();
                    }
                } else {
                    moduleObj = (IModule) moduleValue.traverse(oc).readValueAs(moduleClass);
                }
                configuration.modules.add(moduleObj);
            } else {
                ChatDirector.logDebug("Not adding module " + module + " it failed to load.");
            }
        }
        // Remove modules that failed to load
        while (configuration.modules.contains(null)) {
            configuration.modules.remove(null);
        }
        System.out.println(ChatDirector.config.getModules());
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
