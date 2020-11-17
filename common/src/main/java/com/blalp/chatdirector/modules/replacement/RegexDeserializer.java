package com.blalp.chatdirector.modules.replacement;

import java.io.IOException;
import java.util.Map.Entry;

import com.blalp.chatdirector.model.IteratorIterable;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class RegexDeserializer extends JsonDeserializer<RegexItem> {

    @Override
    public RegexItem deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        ObjectCodec oc = p.getCodec();
        JsonNode config = oc.readTree(p);
        RegexItem output = new RegexItem();
        for (Entry<String, JsonNode> pair : new IteratorIterable<>(config.fields())) {
            output.pairs.put(pair.getKey(), pair.getValue().asText());
        }
        return output;
    }

}
