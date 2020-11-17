package com.blalp.chatdirector.modules.logic;

import java.io.IOException;
import java.util.Map.Entry;

import com.blalp.chatdirector.configuration.Chain;
import com.blalp.chatdirector.model.IteratorIterable;
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

        for (Entry<String, JsonNode> jsonChain : new IteratorIterable<>(config.fields())) {
            output.chains.add(jsonChain.getValue().traverse(oc).readValueAs(Chain.class));
        }
        return output;
    }
}