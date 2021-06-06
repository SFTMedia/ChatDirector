package com.blalp.chatdirector.legacyConfig.modules.common;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class EchoItemDeserializer_v0_1_5 extends JsonDeserializer<EchoItem_v0_1_5> {
    @Override
    public EchoItem_v0_1_5 deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        ObjectCodec oc = p.getCodec();
        JsonNode config = oc.readTree(p);
        EchoItem_v0_1_5 item = new EchoItem_v0_1_5(config.asText());
        return item;
    }
}
