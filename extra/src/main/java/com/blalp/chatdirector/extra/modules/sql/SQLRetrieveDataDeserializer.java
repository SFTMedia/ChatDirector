package com.blalp.chatdirector.extra.modules.sql;

import java.io.IOException;

import com.blalp.chatdirector.ChatDirector;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class SQLRetrieveDataDeserializer extends JsonDeserializer<SQLRetrieveDataItem> {

    @Override
    public SQLRetrieveDataItem deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        ObjectCodec oc = p.getCodec();
        JsonNode config = oc.readTree(p);
        SQLRetrieveDataItem output = new SQLRetrieveDataItem();
        if (config.has("cache")) {
            output.cache = config.get("cache").asBoolean();
        }
        output.connection = config.get("connection").asText();
        output.key = config.get("key").asText();
        output.name = config.get("name").asText();
        output.table = config.get("table").asText();
        ChatDirector.getConfigStaging().getOrCreateDaemon(SQLConnections.class).addItem(output);
        return output;
    }

}
