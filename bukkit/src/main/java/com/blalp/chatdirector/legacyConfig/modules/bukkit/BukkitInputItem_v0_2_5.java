package com.blalp.chatdirector.legacyConfig.modules.bukkit;

import java.util.List;

import com.blalp.chatdirector.core.model.ILegacyItem;
import com.blalp.chatdirector.core.model.Version;

import lombok.Data;

@Data
public class BukkitInputItem_v0_2_5 implements ILegacyItem {
    boolean chat = false, checkCanceled = false, login = false, logout = false, serverStarted = false,
            serverStopped = false, newPlayer = false, cancelChat = false;
    String formatChat = "%PLAYER_NAME%: %CHAT_MESSAGE%";
    String formatLogin = "**%PLAYER_NAME% joined the server**";
    String formatNewPlayer = "**Welcome %PLAYER_NAME%!**";
    String formatLogout = "**%PLAYER_NAME% joined the server**";
    String formatStopped = "**Server Stopped**";
    String formatStarted = "**Server Started**";
    boolean overrideChat = false;

    @Override
    public List<ILegacyItem> updateToNextLegacyItems() {
        return null;
    }
    @Override
    public Version nextUpdateVersion() {
        return new Version();
    }
    
}
