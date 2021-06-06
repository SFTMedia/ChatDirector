package com.blalp.chatdirector.legacyConfig.modules.discord;

import java.util.List;
import java.util.Map;

import com.blalp.chatdirector.core.model.ILegacyItem;
import com.blalp.chatdirector.core.model.Version;

import lombok.Data;

@Data
public class DiscordEmbedItem_v0_2_0 implements ILegacyItem {
    String channel;
    String bot;

    String title, description, authorName, authorLink, authorAvatar, color, footerName, footerAvatar, image, thumbnail;
    Map<String, String> fields, inlineFields;

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
        return "discord-embed";
    }

}
