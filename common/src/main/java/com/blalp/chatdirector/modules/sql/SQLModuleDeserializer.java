package com.blalp.chatdirector.modules.sql;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map.Entry;

import com.blalp.chatdirector.model.IteratorIterable;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class SQLModuleDeserializer extends JsonDeserializer<SQLModule> {

    @Override
    public SQLModule deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        ObjectCodec oc = p.getCodec();
        JsonNode node = oc.readTree(p);
        SQLModule module = new SQLModule();
        for (Entry<String, JsonNode> connectionEntry : new IteratorIterable<>(node.get("connections").fields())) {
            SQLConnection connection = new SQLConnection(connectionEntry.getValue().asText());
            SQLModule.connections.put(connectionEntry.getKey(), connection);
            SQLModule.tables.put(connectionEntry.getKey(), new ArrayList<>());
        }
        return module;
    }

}
