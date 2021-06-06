package com.blalp.chatdirector.minecraft.model;

import java.util.ArrayList;
import java.util.List;

import com.blalp.chatdirector.core.ChatDirector;
import com.blalp.chatdirector.minecraft.utils.FancyMessageDeserializer;
import com.blalp.chatdirector.minecraft.utils.FancyMessageEnum;
import com.blalp.chatdirector.minecraft.utils.FancyMessageSerializer;
import com.blalp.chatdirector.core.model.Context;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonDeserialize(using = FancyMessageDeserializer.class)
@JsonSerialize(using = FancyMessageSerializer.class)
@Data
@EqualsAndHashCode
public class FancyMessage {
    String text, color = null;
    List<FancyMessage> next = new ArrayList<>();
    boolean bold, italics, strikethrough, obfuscated;
    FancyMessageEnum click = FancyMessageEnum.NONE, hover = FancyMessageEnum.NONE;
    String clickData, hoverData;

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
