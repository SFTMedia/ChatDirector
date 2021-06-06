package com.blalp.chatdirector.legacyConfig.modules.discord;

import java.util.List;

import com.blalp.chatdirector.core.model.ILegacyItem;
import com.blalp.chatdirector.core.model.Version;

import lombok.Data;

@Data
public class DiscordOutputReactionItem_v0_2_0 implements ILegacyItem {
    String emoji, channel, message;
    boolean add;
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
        return "discord-output-reaction";
    }

}
