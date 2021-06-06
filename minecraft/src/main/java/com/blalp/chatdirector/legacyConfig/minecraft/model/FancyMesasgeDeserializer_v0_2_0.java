package com.blalp.chatdirector.legacyConfig.minecraft.model;

import java.io.IOException;

import com.blalp.chatdirector.minecraft.utils.FancyMessageEnum;
import com.blalp.chatdirector.core.model.IteratorIterable;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class FancyMesasgeDeserializer_v0_2_0 extends JsonDeserializer<FancyMessage_v0_2_0> {

    @Override
    public FancyMessage_v0_2_0 deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        ObjectCodec oc = p.getCodec();
        JsonNode data = oc.readTree(p);
        FancyMessage_v0_2_0 output = new FancyMessage_v0_2_0();
        if (data == null) {
            System.err.println("Fancy text object resolved to null, this shouldn't happen, check your YAML.");
        }
        if (data.isTextual()) {
            return new FancyMessage_v0_2_0(data.asText());
        } else if (data.isArray()) {
            // This should be formed in a map
            for (JsonNode node : new IteratorIterable<JsonNode>(data.elements())) {
                if (node.isEmpty()) {
                    System.err.println("Fancy text nesting invalid, check your YAML.");
                }
                for (JsonNode innerNode : new IteratorIterable<JsonNode>(node.elements())) {
                    if (output == null) {
                        output = innerNode.traverse(oc).readValueAs(FancyMessage_v0_2_0.class);
                    } else {
                        output.append(innerNode.traverse(oc).readValueAs(FancyMessage_v0_2_0.class));
                    }
                }
            }
        } else {
            // This is the actual map
            if (!data.has("text")) {
                System.err.println("text is null in a fancy message, this shouldn't happen, check your YAML.");
            }
            output = data.get("text").traverse(oc).readValueAs(FancyMessage_v0_2_0.class);
            if (data.has("click-file")) {
                output.setClickEvent(FancyMessageEnum.OPEN_FILE, data.get("click-file").asText());
            }
            if (data.has("click-change-page")) {
                output.setClickEvent(FancyMessageEnum.CHANGE_PAGE, data.get("click-change-page").asText());
            }
            if (data.has("click-run-command")) {
                output.setClickEvent(FancyMessageEnum.RUN_COMMAND, data.get("click-run-command").asText());
            }
            if (data.has("click-suggest-command")) {
                output.setClickEvent(FancyMessageEnum.SUGGEST_COMMAND, data.get("click-suggest-command").asText());
            }
            if (data.has("click-url")) {
                output.setClickEvent(FancyMessageEnum.OPEN_URL, data.get("click-url").asText());
            }
            if (data.has("color")) {
                output.setColor(data.get("color").asText());
            }
            if (data.has("bold")) {
                output.setBold(data.get("bold").asBoolean());
            }
            if (data.has("italics")) {
                output.setItalics(data.get("italics").asBoolean());
            }
            if (data.has("strikethrough")) {
                output.setStrikethrough(data.get("strikethrough").asBoolean());
            }
            if (data.has("obfuscated")) {
                output.setObfuscated(data.get("obfuscated").asBoolean());
            }
            if (data.has("show-achievement")) {
                output.setHoverEvent(FancyMessageEnum.SHOW_ACHIEVEMENT, data.get("show-achievement").asText());
            }
            if (data.has("show-entity")) {
                output.setHoverEvent(FancyMessageEnum.SHOW_ENTITY, data.get("show-entity").asText());
            }
            if (data.has("show-item")) {
                output.setHoverEvent(FancyMessageEnum.SHOW_ITEM, data.get("show-item").asText());
            }
            if (data.has("show-text")) {
                output.setHoverEvent(FancyMessageEnum.SHOW_TEXT, data.get("show-text").asText());
            }
        }
        return output;
    }

}
