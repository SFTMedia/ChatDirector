package com.blalp.chatdirector.legacyConfig.modules.cache;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CacheIfItemSerializer_v0_2_0 extends JsonSerializer<CacheIfItem_v0_2_0> {

    @Override
    public void serialize(CacheIfItem_v0_2_0 value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException {
        gen.writeStartObject();
        gen.writeStringField("key", value.getKey());
        gen.writeStringField("source", value.getSource());
        gen.writeObjectField("yes-chain", value.getYesChain());
        gen.writeObjectField("no-chain", value.getNoChain());
        gen.writeEndObject();
    }
    
}
