package com.blalp.chatdirector.legacyConfig.modules.bukkit;

import java.util.Arrays;
import java.util.List;

import com.blalp.chatdirector.core.model.ILegacyItem;
import com.blalp.chatdirector.core.model.Version;

import lombok.Data;

@Data
public class BukkitInputItem_v0_2_0 implements ILegacyItem {
    boolean chat = false, checkCanceled = false, join = false, leave = false, serverStarted = false,
            serverStopped = false, newJoin = false, cancelChat = false;
    String formatChat = "%PLAYER_NAME%: %CHAT_MESSAGE%";
    String formatLogin = "**%PLAYER_NAME% joined the server**";
    String formatNewPlayer = "**Welcome %PLAYER_NAME%!**";
    String formatLogout = "**%PLAYER_NAME% joined the server**";
    String formatStopped = "**Server Stopped**";
    String formatStarted = "**Server Started**";
    boolean overrideChat = false;


    @Override
    public List<ILegacyItem> updateToNextLegacyItems() {
        BukkitInputItem_v0_2_5 output = new BukkitInputItem_v0_2_5();
        output.login=join;
        output.logout=leave;
        output.cancelChat=cancelChat;
        output.checkCanceled=checkCanceled;
        output.chat=chat;
        output.serverStarted=serverStarted;
        output.serverStopped=serverStopped;
        output.newPlayer=newJoin;
        output.formatChat=formatChat;
        output.formatLogin=formatLogin;
        output.formatNewPlayer=formatNewPlayer;
        output.formatLogout=formatLogout;
        output.formatStarted=formatStarted;
        output.formatStopped=formatStopped;
        output.overrideChat=overrideChat;
        return Arrays.asList(output);
    }

    @Override
    public Version nextUpdateVersion() {
        return new Version(0,2,0);
    }
    
}
