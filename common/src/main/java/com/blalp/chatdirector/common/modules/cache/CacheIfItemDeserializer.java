package com.blalp.chatdirector.common.modules.cache;

import java.io.IOException;

import com.blalp.chatdirector.configuration.Chain;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class CacheIfItemDeserializer extends JsonDeserializer<CacheIfItem> {

    @Override
    public CacheIfItem deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        ObjectCodec oc = p.getCodec();
        JsonNode config = oc.readTree(p);
        CacheIfItem item = new CacheIfItem();
        item.setNoChain(config.get("no-chain").traverse(oc).readValueAs(Chain.class));
        item.setYesChain(config.get("yes-chain").traverse(oc).readValueAs(Chain.class));
        item.setKey(config.get("key").asText());
        return item;
    }

}
