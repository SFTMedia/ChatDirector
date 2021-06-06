package com.blalp.chatdirector.legacyConfig.modules.luckperms;

import java.io.IOException;

import com.blalp.chatdirector.core.configuration.LegacyChain;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class LuckPermsHasItemDeserializer_v0_2_0 extends JsonDeserializer<LuckPermsHasItem_v0_2_0> {

    @Override
    public LuckPermsHasItem_v0_2_0 deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        ObjectCodec oc = p.getCodec();
        JsonNode config = oc.readTree(p);
        LuckPermsHasItem_v0_2_0 output = new LuckPermsHasItem_v0_2_0();
        if (config.has("yes-chain")) {
            output.setYesChain(config.get("yes-chain").traverse(oc).readValueAs(LegacyChain.class));
        }
        if (config.has("no-chain")) {
            output.setNoChain(config.get("no-chain").traverse(oc).readValueAs(LegacyChain.class));
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
