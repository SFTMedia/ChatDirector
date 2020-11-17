package com.blalp.chatdirector.utils;

import java.io.IOException;

import com.blalp.chatdirector.model.IteratorIterable;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class FancyMessageDeserializer extends JsonDeserializer<FancyMessage> {

    @Override
    public FancyMessage deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        ObjectCodec oc = p.getCodec();
        JsonNode data = oc.readTree(p);
        FancyMessage output = new FancyMessage();
        if (data == null) {
            System.err.println("Fancy text object resolved to null, this shouldn't happen, check your YAML.");
        }
        if (data.isTextual()) {
            return new FancyMessage(data.asText());
        } else if (data.isArray()) {
            for (JsonNode nestedData : new IteratorIterable<JsonNode>(data.elements())) {
                if (output == null) {
                    output = nestedData.traverse(oc).readValueAs(FancyMessage.class);
                } else {
                    output.append(nestedData.traverse(oc).readValueAs(FancyMessage.class));
                }
            }
        } else {
            // This is the actual map
            output = data.at("text").traverse(oc).readValueAs(FancyMessage.class);
            for (JsonNode node : new IteratorIterable<JsonNode>(data.elements())) {
                if (node.has("click-file")) {
                    output.setClickEvent(FancyMessageEnum.OPEN_FILE, data.get("click-file").asText());
                }
                if (node.has("click-change-page")) {
                    output.setClickEvent(FancyMessageEnum.CHANGE_PAGE, data.get("click-change-page").asText());
                }
                if (node.has("click-run-command")) {
                    output.setClickEvent(FancyMessageEnum.RUN_COMMAND, data.get("click-run-command").asText());
                }
                if (node.has("click-suggest-command")) {
                    output.setClickEvent(FancyMessageEnum.SUGGEST_COMMAND, data.get("click-suggest-command").asText());
                }
                if (node.has("click-url")) {
                    output.setClickEvent(FancyMessageEnum.OPEN_URL, data.get("click-url").asText());
                }
                if (node.has("color")) {
                    output.setColor(data.get("color").asText());
                }
                if (node.has("bold")) {
                    output.setBold(data.get("bold").asBoolean());
                }
                if (node.has("italics")) {
                    output.setItalics(data.get("italics").asBoolean());
                }
                if (node.has("strikethrough")) {
                    output.setStrikethrough(data.get("strikethrough").asBoolean());
                }
                if (node.has("obfuscated")) {
                    output.setObfuscated(data.get("obfuscated").asBoolean());
                }
                if (node.has("show-achievement")) {
                    output.setHoverEvent(FancyMessageEnum.SHOW_ACHIEVEMENT, data.get("show-achievement").asText());
                }
                if (node.has("show-entity")) {
                    output.setHoverEvent(FancyMessageEnum.SHOW_ENTITY, data.get("show-entity").asText());
                }
                if (node.has("show-item")) {
                    output.setHoverEvent(FancyMessageEnum.SHOW_ITEM, data.get("show-item").asText());
                }
                if (node.has("show-text")) {
                    output.setHoverEvent(FancyMessageEnum.SHOW_TEXT, data.get("show-text").asText());
                }
            }
        }
        return output;
    }

}
