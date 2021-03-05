package com.blalp.chatdirector.modules.javacord;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class DiscordInputItemDeserializer extends JsonDeserializer<DiscordInputItem> {

    @Override
    public DiscordInputItem deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        ObjectCodec oc = p.getCodec();
        JsonNode node = oc.readTree(p);
        DiscordInputItem item = new DiscordInputItem();
        if (node.has("reaction-add-event") && node.get("reaction-add-event").isBoolean()) {
            item.reactionAddEvent = node.get("reaction-add-event").asBoolean();
        }
        if (node.has("reaction-remove-event") && node.get("reaction-remove-event").isBoolean()) {
            item.reactionRemoveEvent = node.get("reaction-remove-event").asBoolean();
        }
        if (node.has("message-event") && node.get("message-event").isBoolean()) {
            item.messageEvent = node.get("message-event").asBoolean();
        }
        if (node.has("channel") && node.get("channel").isTextual()) {
            item.channel = node.get("channel").asText();
        }
        if (node.has("category") && node.get("category").isTextual()) {
            item.category = node.get("category").asText();
        }
        if (node.has("message") && node.get("message").isTextual()) {
            item.message = node.get("message").asText();
        }
        if (node.has("format") && node.get("format").isTextual()) {
            item.format = node.get("format").asText();
        }
        if (node.has("bot") && node.get("bot").isTextual()) {
            item.bot = node.get("bot").asText();
        }
        DiscordBot.addPendingItems(item.bot, item);
        return item;
    }

}
