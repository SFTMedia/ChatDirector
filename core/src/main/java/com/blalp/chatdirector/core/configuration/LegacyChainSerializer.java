package com.blalp.chatdirector.core.configuration;

import java.io.IOException;

import com.blalp.chatdirector.core.model.ILegacyItem;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class LegacyChainSerializer extends JsonSerializer<LegacyChain> {

    @Override
    public void serialize(LegacyChain value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartArray();
        for (ILegacyItem iLegacyItem : value.getItems()) {
            gen.writeStartObject();
            gen.writeFieldName(iLegacyItem.name());
            gen.writeObject(iLegacyItem);
            gen.writeEndObject();
        }
        gen.writeEndArray();
    }

}
