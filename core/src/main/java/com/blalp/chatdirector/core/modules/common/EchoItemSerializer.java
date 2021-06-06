package com.blalp.chatdirector.core.modules.common;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class EchoItemSerializer extends JsonSerializer<EchoItem> {

    @Override
    public void serialize(EchoItem value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.format);
    }

}
