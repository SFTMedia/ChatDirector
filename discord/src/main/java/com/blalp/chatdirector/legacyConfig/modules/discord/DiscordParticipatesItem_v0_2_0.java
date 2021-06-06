package com.blalp.chatdirector.legacyConfig.modules.discord;

import java.util.List;

import com.blalp.chatdirector.core.configuration.Chain;
import com.blalp.chatdirector.core.model.ILegacyItem;
import com.blalp.chatdirector.core.model.Version;

import lombok.Data;

@Data
public class DiscordParticipatesItem_v0_2_0 implements ILegacyItem {
    int length = 50;
    Chain each;
    String channel,bot;
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
        return "discord-participants";
    }

    
}
