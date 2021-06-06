package com.blalp.chatdirector.legacyConfig.modules.discord;

import java.util.List;

import com.blalp.chatdirector.core.model.ILegacyItem;
import com.blalp.chatdirector.core.model.Version;

import lombok.Data;

@Data
public class DiscordMessageHistoryItem_v0_2_0 implements ILegacyItem {
    String format = "%DISCORD_AUTHOR_DISPLAY_NAME% (%DISCORD_AUTHOR_ID%): %DISCORD_MESSAGE%";
    int length = 50;
    String channel;
    String bot;

    @Override
    public List<ILegacyItem> updateToNextLegacyItems() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Version nextUpdateVersion() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String name() {
        // TODO Auto-generated method stub
        return "discord-message-history";
    }

}
