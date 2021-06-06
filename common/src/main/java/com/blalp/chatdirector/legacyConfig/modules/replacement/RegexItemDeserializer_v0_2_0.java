package com.blalp.chatdirector.legacyConfig.modules.replacement;

import java.io.IOException;
import java.util.Map.Entry;

import com.blalp.chatdirector.core.model.IteratorIterable;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class RegexItemDeserializer_v0_2_0 extends JsonDeserializer<RegexItem_v0_2_0> {

    @Override
    public RegexItem_v0_2_0 deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        ObjectCodec oc = p.getCodec();
        JsonNode config = oc.readTree(p);
        RegexItem_v0_2_0 output = new RegexItem_v0_2_0();
        for (JsonNode pairObj : new IteratorIterable<>(config.elements())) {
            for (Entry<String, JsonNode> pair : new IteratorIterable<>(pairObj.fields())) {
                output.pairs.put(pair.getKey(), pair.getValue().asText());
            }
        }
        return output;
    }

}
