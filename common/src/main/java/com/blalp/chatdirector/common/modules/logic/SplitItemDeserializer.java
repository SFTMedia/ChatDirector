package com.blalp.chatdirector.common.modules.logic;

import java.io.IOException;
import java.util.Map.Entry;

import com.blalp.chatdirector.core.configuration.Chain;
import com.blalp.chatdirector.core.model.IteratorIterable;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class SplitItemDeserializer extends JsonDeserializer<SplitItem> {

    @Override
    public SplitItem deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        ObjectCodec oc = p.getCodec();
        JsonNode config = oc.readTree(p);
        SplitItem output = new SplitItem();

        for (Entry<String, JsonNode> chain: new IteratorIterable<>(config.fields())) {
            output.getChains().put(chain.getKey(),chain.getValue().traverse(oc).readValueAs(Chain.class));
        }
        return output;
    }
}