package com.blalp.chatdirector.legacyConfig.modules.common;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class EchoItemSerializer_v0_1_5 extends JsonSerializer<EchoItem_v0_1_5> {

    @Override
    public void serialize(EchoItem_v0_1_5 value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.format);
    }
    
}
