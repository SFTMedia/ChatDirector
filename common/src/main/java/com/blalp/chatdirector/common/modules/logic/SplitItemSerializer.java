package com.blalp.chatdirector.common.modules.logic;

import java.io.IOException;

import com.blalp.chatdirector.core.configuration.Chain;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class SplitItemSerializer extends JsonSerializer<SplitItem> {

    @Override
    public void serialize(SplitItem value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartArray();
        for (Chain chain : value.getChains().values()) {
            gen.writeObject(chain);
        }
        gen.writeEndArray();
    }
}