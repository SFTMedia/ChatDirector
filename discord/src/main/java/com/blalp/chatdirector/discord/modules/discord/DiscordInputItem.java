package com.blalp.chatdirector.discord.modules.discord;

import com.blalp.chatdirector.core.ChatDirector;
import com.blalp.chatdirector.core.model.IDaemon;
import com.blalp.chatdirector.core.utils.ValidationUtils;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@Data
@EqualsAndHashCode(callSuper = true)
@JsonDeserialize(using = DiscordInputItemDeserializer.class)
@ToString(callSuper = true)
public class DiscordInputItem extends DiscordItem {
    boolean reactionAddEvent = false;
    boolean reactionRemoveEvent = false;
    boolean messageEvent = false;
    String channel, category, message, format = "%DISCORD_MESSAGE%";

    @Override
    public boolean isValid() {
        if (!super.isValid()) {
            return false;
        }
        if (messageEvent && ValidationUtils.hasContent(format)) {
            return ValidationUtils.anyOf(ValidationUtils.hasContent(channel), ValidationUtils.hasContent(category));
        } else if (reactionAddEvent || reactionRemoveEvent) {
            return ValidationUtils.anyOf(ValidationUtils.hasContent(channel), ValidationUtils.hasContent(category),
                    ValidationUtils.hasContent(message));
        } else {
            return false;
        }
    }

    public DiscordInputItem() {
        ((IDaemon) ChatDirector.getConfigStaging().getOrCreateDaemon(DiscordInputDaemon.class)).addItem(this);
    }

}
