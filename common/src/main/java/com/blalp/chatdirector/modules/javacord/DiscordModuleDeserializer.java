package com.blalp.chatdirector.modules.javacord;

import java.io.IOException;
import java.util.Map.Entry;

import com.blalp.chatdirector.model.IteratorIterable;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class DiscordModuleDeserializer extends JsonDeserializer<DiscordModule> {

    @Override
    public DiscordModule deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        ObjectCodec oc = p.getCodec();
        JsonNode node = oc.readTree(p);
        DiscordModule module = new DiscordModule();
        for (Entry<String, JsonNode> botMap : new IteratorIterable<>(node.get("bots").fields())) {
            DiscordBot bot = new DiscordBot(botMap.getValue().asText());
            module.discordBots.put(botMap.getKey(), bot);
        }
        return module;
    }

}
