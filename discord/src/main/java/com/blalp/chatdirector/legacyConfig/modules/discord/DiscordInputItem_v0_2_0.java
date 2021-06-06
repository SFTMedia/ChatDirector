package com.blalp.chatdirector.legacyConfig.modules.discord;

import java.util.List;

import com.blalp.chatdirector.core.model.ILegacyItem;
import com.blalp.chatdirector.core.model.Version;

import lombok.Data;

@Data
public class DiscordInputItem_v0_2_0 implements ILegacyItem {
    boolean reactionAddEvent = false;
    boolean reactionRemoveEvent = false;
    boolean messageEvent = false;
    String channel, category, message, format = "%DISCORD_MESSAGE%";
    String bot;

    @Override
    public List<ILegacyItem> updateToNextLegacyItems() {
        return null;
    }

    @Override
    public Version nextUpdateVersion() {
        return null;
    }

    @Override
    public String name() {
        return "discord-input";
    }

}
