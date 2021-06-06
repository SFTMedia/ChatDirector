package com.blalp.chatdirector.legacyConfig.modules.discord;

import java.util.Arrays;
import java.util.List;

import com.blalp.chatdirector.core.model.ILegacyItem;
import com.blalp.chatdirector.core.model.ILegacyModule;
import com.blalp.chatdirector.core.model.Version;

public class DiscordLegacyConfig implements ILegacyModule {

    @Override
    public List<String> getItemNames(Version version) {
        return Arrays.asList("discord-input", "discord-output", "discord-output-file", "discord-output-reaction",
                "discord-resolve", "discord-embed", "discord-get-dm-channel", "discord-message-history",
                "discord-create-channel", "discord-delete-channel", "discord-rename-channel", "discord-participants",
                "discord-delete-message");
    }

    @Override
    public Class<? extends ILegacyItem> getItemClass(String type, Version version) {
        switch (type) {
        case "discord-input":
            return DiscordInputItem_v0_2_0.class;
        case "discord-output":
            return DiscordOutputItem_v0_2_0.class;
        case "discord-output-file":
            return DiscordOutputFileItem_v0_2_0.class;
        case "discord-output-reaction":
            return DiscordOutputReactionItem_v0_2_0.class;
        case "discord-resolve":
            return DiscordResolveItem_v0_2_0.class;
        case "discord-embed":
            return DiscordEmbedItem_v0_2_0.class;
        case "discord-get-dm-channel":
            return DiscordGetDMChannelItem_v0_2_0.class;
        case "discord-message-history":
            return DiscordMessageHistoryItem_v0_2_0.class;
        case "discord-create-channel":
            return DiscordCreateChannelItem_v0_2_0.class;
        case "discord-delete-channel":
            return DiscordDeleteChannel_v0_2_0.class;
        case "discord-delete-message":
            return DiscordDeleteMessage_v0_2_0.class;
        case "discord-rename-channel":
            return DiscordChannelRename_v0_2_0.class;
        case "discord-participants":
            return DiscordParticipatesItem_v0_2_0.class;
        default:
            return null;
        }
    }

}
