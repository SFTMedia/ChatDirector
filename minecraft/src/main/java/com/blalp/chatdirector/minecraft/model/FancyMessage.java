package com.blalp.chatdirector.abstractions.fancymessage;

import java.util.ArrayList;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Context;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = FancyMessageDeserializer.class)
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
}
