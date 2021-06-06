package com.blalp.chatdirector.legacyConfig.modules.bungee;

import java.util.List;

import com.blalp.chatdirector.core.model.ILegacyItem;
import com.blalp.chatdirector.core.model.Version;

import lombok.Data;

@Data
public class BungeeInputItem_v0_2_5 implements ILegacyItem {

    String formatLogout = "&0[&4<-&0] &e%PLAYER_NAME% has left the network from %SERVER_NAME%";
    String formatSwitchServers = "&0[&e<->&0] &e%PLAYER_NAME% has switched to %SERVER_NAME%";
    String formatChat = "[%SERVER_NAME%] %PLAYER_NAME%: %CHAT_MESSAGE%";
    String formatJoin = "&0[&2->&0] &e%PLAYER_NAME% joined the network on %SERVER_NAME%!";
    boolean logout, switchServers, chat, login;
    boolean command = false;
    boolean overrideChat = false;
    
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
        return "bungee-input";
    }
    
}
