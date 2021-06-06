package com.blalp.chatdirector.legacyConfig.modules.logic;

import java.io.IOException;
import java.util.Map.Entry;

import com.blalp.chatdirector.core.configuration.LegacyChain;
import com.blalp.chatdirector.core.model.IteratorIterable;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class SplitItemDeserializer_v0_2_5 extends JsonDeserializer<SplitItem_v0_2_5> {

    @Override
    public SplitItem_v0_2_5 deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        ObjectCodec oc = p.getCodec();
        JsonNode config = oc.readTree(p);
        SplitItem_v0_2_5 output = new SplitItem_v0_2_5();

        for (Entry<String, JsonNode> chain : new IteratorIterable<>(config.fields())) {
            output.chains.put(chain.getKey(),chain.getValue().traverse(oc).readValueAs(LegacyChain.class));
        }
        return output;
    }
}