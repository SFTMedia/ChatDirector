package com.blalp.chatdirector.legacyConfig.minecraft.model;

import java.util.ArrayList;
import java.util.List;

import com.blalp.chatdirector.minecraft.utils.FancyMessageDeserializer;
import com.blalp.chatdirector.minecraft.utils.FancyMessageEnum;
import com.blalp.chatdirector.minecraft.utils.FancyMessageSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonDeserialize(using = FancyMessageDeserializer.class)
@JsonSerialize(using = FancyMessageSerializer.class)
@Data
@EqualsAndHashCode
public class FancyMessage_v0_2_0 {
    String text, color = null;
    List<FancyMessage_v0_2_0> next = new ArrayList<>();
    boolean bold, italics, strikethrough, obfuscated;
    FancyMessageEnum click = FancyMessageEnum.NONE, hover = FancyMessageEnum.NONE;
    String clickData, hoverData;

    public FancyMessage_v0_2_0(String data) {
        text = data;
    }

    public FancyMessage_v0_2_0() {
        text = "";
    }

    public void append(FancyMessage_v0_2_0 fancyMessage) {
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
