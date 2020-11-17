package com.blalp.chatdirector.model.fancychat;

import java.util.ArrayList;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.IteratorIterable;
import com.fasterxml.jackson.databind.JsonNode;

public class FancyMessage {
    public String text, color = null;
    public ArrayList<FancyMessage> next = new ArrayList<>();
    public boolean bold, italics, strikethrough, obfuscated;
    public FancyMessageEnum click = FancyMessageEnum.NONE, hover = FancyMessageEnum.NONE;
    public String clickData, hoverData;

    public FancyMessage(String data) {
        text = data;
    }

    public FancyMessage() {
        text = "";
    }

    public FancyMessage withContext(Context context) {
        text = ChatDirector.format(text, context);
        for (FancyMessage message : next) {
            message.withContext(context);
        }
        clickData = ChatDirector.format(clickData, context);
        hoverData = ChatDirector.format(hoverData, context);
        return this;
    }

    public FancyMessage duplicate() {
        FancyMessage output = new FancyMessage();
        output.text = text;
        output.color = color;
        for (FancyMessage message : next) {
            output.next.add(message.duplicate());
        }
        output.bold = bold;
        output.italics = italics;
        output.strikethrough = strikethrough;
        output.obfuscated = obfuscated;
        output.click = click;
        output.hover = hover;
        output.clickData = clickData;
        output.hoverData = hoverData;
        return output;
    }

    public void append(FancyMessage fancyMessage) {
        next.add(fancyMessage);
    }

    public void setClickEvent(FancyMessageEnum clickType, String string) {
        click = clickType;
        clickData = string;
    }

    public void setHoverEvent(FancyMessageEnum hoverType, String string) {
        hover = hoverType;
        hoverData = string;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public void setItalics(boolean italics) {
        this.italics = italics;
    }

    public void setStrikethrough(boolean strikethrough) {
        this.strikethrough = strikethrough;
    }

    public void setObfuscated(boolean obfuscated) {
        this.obfuscated = obfuscated;
    }

    public static FancyMessage parse(JsonNode data) {
        FancyMessage output = new FancyMessage();
        if (data == null) {
            System.err.println("Fancy text object resolved to null, this shouldn't happen, check your YAML.");
        }
        if (data.isTextual()) {
            return new FancyMessage(data.asText());
        } else if (data.isArray()) {
            for (JsonNode nestedData : new IteratorIterable<JsonNode>(data.elements())) {
                if (output == null) {
                    output = parse(nestedData);
                } else {
                    output.append(parse(nestedData));
                }
            }
        } else {
            // This is the actual map
            output = parse(data.at("text"));
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
