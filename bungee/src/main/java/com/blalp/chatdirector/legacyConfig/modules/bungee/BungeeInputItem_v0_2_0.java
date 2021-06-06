package com.blalp.chatdirector.legacyConfig.modules.bungee;

import java.util.Arrays;
import java.util.List;

import com.blalp.chatdirector.core.model.ILegacyItem;
import com.blalp.chatdirector.core.model.Version;

import lombok.Data;

@Data
public class BungeeInputItem_v0_2_0 implements ILegacyItem {

    String formatDisconnect = "&0[&4<-&0] &e%PLAYER_NAME% has left the network from %SERVER_NAME%";
    String formatSwitchServers = "&0[&e<->&0] &e%PLAYER_NAME% has switched to %SERVER_NAME%";
    String formatChat = "[%SERVER_NAME%] %PLAYER_NAME%: %CHAT_MESSAGE%";
    String formatJoin = "&0[&2->&0] &e%PLAYER_NAME% joined the network on %SERVER_NAME%!";
    boolean disconnect, switchServers, chat, join;
    boolean command = false;
    boolean overrideChat = false;

    @Override
    public List<ILegacyItem> updateToNextLegacyItems() {
        BungeeInputItem_v0_2_5 output = new BungeeInputItem_v0_2_5();
        output.formatChat = formatChat;
        output.formatJoin = formatJoin;
        output.formatLogout = formatDisconnect;
        output.formatSwitchServers = formatSwitchServers;
        output.logout = disconnect;
        output.switchServers = switchServers;
        output.chat = chat;
        output.login = join;
        output.command = command;
        output.overrideChat = overrideChat;
        return Arrays.asList(output);
    }

    @Override
    public Version nextUpdateVersion() {
        return new Version(0, 2, 5);
    }

    @Override
    public String name() {
        return "bungee-input";
    }

}
