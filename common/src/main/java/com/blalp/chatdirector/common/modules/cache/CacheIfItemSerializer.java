package com.blalp.chatdirector.common.modules.cache;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CacheIfItemSerializer extends JsonSerializer<CacheIfItem> {

    @Override
    public void serialize(CacheIfItem value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("key", value.getKey());
        gen.writeStringField("source", value.getSource());
        gen.writeObjectField("yes-chain", value.getYesChain());
        gen.writeObjectField("no-chain", value.getNoChain());
        gen.writeEndObject();
    }

}
