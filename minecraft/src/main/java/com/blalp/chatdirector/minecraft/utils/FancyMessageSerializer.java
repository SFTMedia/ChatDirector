package com.blalp.chatdirector.minecraft.utils;

import java.io.IOException;

import com.blalp.chatdirector.minecraft.model.FancyMessage;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class FancyMessageSerializer extends JsonSerializer<FancyMessage> {

    @Override
    public void serialize(FancyMessage value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        boolean root = false;
        if ((value.getText() == null || value.getText().equals("")) && value.getClick().equals(FancyMessageEnum.NONE)
                && value.getHover().equals(FancyMessageEnum.NONE) && value.getClickData() == null
                && value.getHoverData() == null && !value.isBold() && !value.isItalics() && !value.isObfuscated()
                && !value.isStrikethrough()) {
            root = true;
            if (!value.getNext().isEmpty()) {
                gen.writeStartArray();
            }
        } else {
            gen.writeStartObject();
            if (!value.getNext().isEmpty()) {
                FancyMessage tempRoot = new FancyMessage();
                tempRoot.setNext(value.getNext());
                gen.writeObjectField("text", tempRoot);
            } else {
                gen.writeStringField("text", value.getText());
            }
            if (value.getText().equals("")) {
                System.out.println(value);
            }
            if (!value.getClick().equals(FancyMessageEnum.NONE)) {
                switch (value.getClick()) {
                case OPEN_FILE:
                    gen.writeStringField("click-file", value.getClickData());
                    break;
                case CHANGE_PAGE:
                    gen.writeStringField("click-change-page", value.getClickData());
                    break;
                case RUN_COMMAND:
                    gen.writeStringField("click-run-command", value.getClickData());
                    break;
                case SUGGEST_COMMAND:
                    gen.writeStringField("click-suggest-command", value.getClickData());
                    break;
                case OPEN_URL:
                    gen.writeStringField("click-url", value.getClickData());
                default:
                    break;
                }
            }
            if (!value.getHover().equals(FancyMessageEnum.NONE)) {
                switch (value.getHover()) {
                case SHOW_ACHIEVEMENT:
                    gen.writeStringField("show-achievement", value.getHoverData());
                    break;
                case SHOW_ENTITY:
                    gen.writeStringField("show-entity", value.getHoverData());
                    break;
                case SHOW_ITEM:
                    gen.writeStringField("show-item", value.getHoverData());
                    break;
                case SHOW_TEXT:
                    gen.writeStringField("show-text", value.getHoverData());
                    break;
                default:
                    break;
                }
            }
            if (value.getColor() != null) {
                gen.writeStringField("color", value.getColor());
            }
            if (value.isBold()) {
                gen.writeBooleanField("bold", value.isBold());
            }
            if (value.isItalics()) {
                gen.writeBooleanField("italics", value.isItalics());
            }
            if (value.isObfuscated()) {
                gen.writeBooleanField("obfuscated", value.isObfuscated());
            }
            if (value.isStrikethrough()) {
                gen.writeBooleanField("strikethrough", value.isStrikethrough());
            }
            gen.writeEndObject();
        }
        if (!value.getNext().isEmpty() && root) {
            for (FancyMessage fancyMessage : value.getNext()) {
                gen.writeStartObject();
                gen.writeObjectField("placeholder", fancyMessage);
                gen.writeEndObject();
            }
            gen.writeEndArray();
        }
    }

}
