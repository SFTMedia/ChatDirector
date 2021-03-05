package com.blalp.chatdirector.extra.modules.luckperms;

import java.io.IOException;

import com.blalp.chatdirector.configuration.Chain;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class LuckPermsHasDeserializer extends JsonDeserializer<LuckPermsHasItem> {

    @Override
    public LuckPermsHasItem deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        ObjectCodec oc = p.getCodec();
        JsonNode config = oc.readTree(p);
        LuckPermsHasItem output = new LuckPermsHasItem();
        if (config.has("yes-chain")) {
            output.setYesChain(config.get("yes-chain").traverse(oc).readValueAs(Chain.class));
        }
        if (config.has("no-chain")) {
            output.setNoChain(config.get("no-chain").traverse(oc).readValueAs(Chain.class));
        }
        if (config.has("permission") && config.get("permission").isTextual()) {
            output.setPermission(config.get("permission").asText());
        }
        if (config.has("source") && config.get("source").isTextual()) {
            output.setSource(config.get("source").asText());
        }
        return output;
    }

}
