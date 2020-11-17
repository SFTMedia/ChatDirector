package com.blalp.chatdirector.configuration;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map.Entry;
import java.util.logging.Level;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.IteratorIterable;
import com.blalp.chatdirector.model.IItem;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class ChainDeserializer extends JsonDeserializer<Chain> {

    @Override
    public Chain deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectCodec oc = p.getCodec();
        JsonNode chain = oc.readTree(p);
        Chain chainObj = new Chain();
        String itemKey = null;
        JsonNode itemValue = null;
        for (JsonNode item : new IteratorIterable<>(chain.elements())) {
            // Traversing items
            if (item.isTextual()) {
                itemKey = item.asText();
                itemValue = null;
            } else {
                for (Entry<String, JsonNode> innerItem : new IteratorIterable<>(item.fields())) {
                    itemKey = innerItem.getKey();
                    itemValue = innerItem.getValue();
                }
            }
            Class<?> moduleClass = ChatDirector.instance.getItemClass(itemKey);
            if (moduleClass != null && IItem.class.isAssignableFrom(moduleClass)) {
                IItem itemObj = null;
                if (itemValue == null || itemValue.isNull()) {
                    try {
                        itemObj = (IItem) moduleClass.getConstructors()[0].newInstance();
                    } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                            | InvocationTargetException | SecurityException e) {
                        e.printStackTrace();
                    }
                } else {
                    itemObj = (IItem) itemValue.traverse(oc).readValueAs(moduleClass);
                }
                chainObj.addItem(itemObj);
            } else {
                ChatDirector.logDebug("Not adding module " + itemValue + " it failed to load.");
            }
        }
        while (chainObj.items.contains(null)) {
            chainObj.items.remove(null);
        }
        if (chainObj.items.size() == 0) {
            ChatDirector.logger.log(Level.WARNING, "No items parsed in chain " + chain);
            return null;
        }
        return chainObj;
    }

}
