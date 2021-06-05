package com.blalp.chatdirector.core.configuration;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map.Entry;
import java.util.logging.Level;

import com.blalp.chatdirector.core.model.IteratorIterable;
import com.blalp.chatdirector.core.ChatDirector;
import com.blalp.chatdirector.core.model.ILegacyItem;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class LegacyChainDeserializer extends JsonDeserializer<LegacyChain> {

    @Override
    public LegacyChain deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectCodec oc = p.getCodec();
        JsonNode chain = oc.readTree(p);
        LegacyChain chainObj = new LegacyChain();
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
            Class<?> itemClass = ChatDirector.getInstance().getItemClass(itemKey);
            if (itemClass != null && ILegacyItem.class.isAssignableFrom(itemClass)) {
                ILegacyItem itemObj = null;
                if (itemValue == null || itemValue.isNull()) {
                    try {
                        itemObj = (ILegacyItem) itemClass.getConstructors()[0].newInstance();
                    } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                            | InvocationTargetException | SecurityException e) {
                        e.printStackTrace();
                    }
                } else {
                    itemObj = (ILegacyItem) itemValue.traverse(oc).readValueAs(itemClass);
                }
                chainObj.addItem(itemObj);
            } else {
                chainObj.setInvalidItem();
                try {
                    ChatDirector.getLogger().log(Level.WARNING,
                            "Not adding item " + itemKey + ":" + itemValue + " it failed to load.");
                } catch (IllegalAccessError e) {
                    // Due to how Jackson works sometimes itemValue cannot be serialized into a
                    // string.
                    ChatDirector.getLogger().log(Level.WARNING, "Not adding item " + itemKey + " it failed to load.");
                }
            }
        }
        while (chainObj.getItems().contains(null)) {
            chainObj.getItems().remove(null);
        }
        if (chainObj.getItems().size() == 0) {
            ChatDirector.getLogger().log(Level.WARNING, "No items parsed in chain " + chain);
            return null;
        }
        return chainObj;
    }

}
