package com.blalp.chatdirector.legacyConfig.modules.cache;

import java.io.IOException;

import com.blalp.chatdirector.core.configuration.LegacyChain;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class CacheIfItemDeserializer_v0_2_0 extends JsonDeserializer<CacheIfItem_v0_2_0> {

    @Override
    public CacheIfItem_v0_2_0 deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        ObjectCodec oc = p.getCodec();
        JsonNode config = oc.readTree(p);
        CacheIfItem_v0_2_0 item = new CacheIfItem_v0_2_0();
        item.setNoChain(config.get("no-chain").traverse(oc).readValueAs(LegacyChain.class));
        item.setYesChain(config.get("yes-chain").traverse(oc).readValueAs(LegacyChain.class));
        item.setKey(config.get("key").asText());
        return item;
    }

}
