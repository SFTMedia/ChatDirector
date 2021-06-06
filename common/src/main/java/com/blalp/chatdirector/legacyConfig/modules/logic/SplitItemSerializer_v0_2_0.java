package com.blalp.chatdirector.legacyConfig.modules.logic;

import java.io.IOException;
import java.util.Map.Entry;

import com.blalp.chatdirector.core.configuration.LegacyChain;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class SplitItemSerializer_v0_2_0 extends JsonSerializer<SplitItem_v0_2_0> {

    @Override
    public void serialize(SplitItem_v0_2_0 value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException {
        gen.writeStartArray();
        for (Entry<String, LegacyChain> chain : value.getChains().entrySet()) {
            gen.writeStartObject();
            gen.writeObjectField(chain.getKey(), chain.getValue());
            gen.writeEndObject();
        }
        gen.writeEndArray();
    }
}