package com.blalp.chatdirector.legacyConfig.modules.sponge;

import java.util.Arrays;
import java.util.List;

import com.blalp.chatdirector.core.model.ILegacyItem;
import com.blalp.chatdirector.core.model.Version;

import lombok.Data;

@Data
public class SpongeInputItem_v0_2_0 implements ILegacyItem {
    boolean chat = false, checkCanceled = false, join = false, leave = false, serverStarted = false,
            serverStopped = false, cancelChat = false;
    String formatChat = "%PLAYER_NAME%: %CHAT_MESSAGE%";
    String formatLogin = "**%PLAYER_NAME% joined the server**";
    String formatLogout = "**%PLAYER_NAME% joined the server**";
    String formatStopped = "**Server Stopped**";
    String formatStarted = "**Server Started**";

    @Override
    public List<ILegacyItem> updateToNextLegacyItems() {
        SpongeInputItem_v0_2_5 output = new SpongeInputItem_v0_2_5();
        output.chat=chat;
        output.checkCanceled=checkCanceled;
        output.login=join;
        output.logout=leave;
        output.serverStarted=serverStarted;
        output.serverStopped=serverStopped;
        output.cancelChat=cancelChat;
        output.formatChat=formatChat;
        output.formatLogin=formatLogin;
        output.formatLogout=formatLogout;
        output.formatStarted=formatStarted;
        output.formatStopped=formatStopped;
        return Arrays.asList(output);
    }

    @Override
    public Version nextUpdateVersion() {
        return new Version(0,2,5);
    }

    @Override
    public String name() {
        return "sponge-input-item";
    }

}
