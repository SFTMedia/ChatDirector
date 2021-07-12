package com.blalp.chatdirector.common.modules.logic;

import java.io.IOException;

import com.blalp.chatdirector.core.configuration.Chain;
import com.blalp.chatdirector.core.model.IteratorIterable;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class SplitDeserializer extends JsonDeserializer<SplitItem> {

    @Override
    public SplitItem deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        ObjectCodec oc = p.getCodec();
        JsonNode config = oc.readTree(p);
        SplitItem output = new SplitItem();

        for (JsonNode chainWithKey : new IteratorIterable<>(config.elements())) {
            for (JsonNode chain : new IteratorIterable<>(chainWithKey.elements())) {
                output.chains.add(chain.traverse(oc).readValueAs(Chain.class));
            }
        }
        return output;
    }
}