package com.blalp.chatdirector.modules.javacord;

import java.util.logging.Level;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.utils.ValidationUtils;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@JsonDeserialize(using = DiscordInputItemDeserializer.class)
public class DiscordInputItem extends DiscordItem {
    boolean reactionAddEvent = false;
    boolean reactionRemoveEvent = false;
    boolean messageEvent = false;
    String channel, category, message, format;

    @Override
    public boolean isValid() {
        if (!super.isValid() || format == null || format.isEmpty()) {
            return false;
        }
        if (!DiscordModule.instance.discordBots.containsKey(bot)) {
            ChatDirector.getLogger().log(Level.WARNING, "Bot " + bot + " not registered for item ." + this);
            return false;
        }
        if (messageEvent) {
            return ValidationUtils.anyOf(ValidationUtils.hasContent(channel), ValidationUtils.hasContent(category));
        } else if (reactionAddEvent || reactionRemoveEvent) {
            return ValidationUtils.anyOf(ValidationUtils.hasContent(channel), ValidationUtils.hasContent(category),
                    ValidationUtils.hasContent(message));
        } else {
            return false;
        }
    }

}
