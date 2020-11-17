package com.blalp.chatdirector.modules.sql;

import java.io.IOException;

import com.blalp.chatdirector.configuration.Chain;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class SQLCacheIfDeserializer extends JsonDeserializer<SQLCacheIfItem> {

    @Override
    public SQLCacheIfItem deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        ObjectCodec oc = p.getCodec();
        JsonNode config = oc.readTree(p);
        SQLCacheIfItem output = new SQLCacheIfItem();
        if (config.has("yes-chain")){
            output.setNoChain(config.get("yes-chain").traverse(oc).readValueAs(Chain.class));
        }
        if (config.has("no-chain")){
            output.setYesChain(config.get("no-chain").traverse(oc).readValueAs(Chain.class));
        }
        if (config.has("invert")&&config.get("invert").isBoolean()) {
            output.setInvert(config.get("invert").asBoolean());
        }
        if (config.has("source")&&config.get("source").isTextual()) {
            output.setSource(config.get("source").asText());
        }
        if(config.has("cache")){
            output.cache=config.get("cache").asBoolean();
        }
        output.connection=config.get("connection").asText();
        output.key=config.get("key").asText();
        output.name=config.get("name").asText();
        output.table=config.get("table").asText();
        SQLModule.tables.get(output.connection).add(output.table);
        return output;
    }
    
}
