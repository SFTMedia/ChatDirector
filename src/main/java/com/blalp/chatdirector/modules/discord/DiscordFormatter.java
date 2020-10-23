package com.blalp.chatdirector.modules.discord;

import java.util.Map;

import com.blalp.chatdirector.internalModules.format.Formatter;

import org.apache.commons.lang.NotImplementedException;

public class DiscordFormatter extends Formatter {

    @Override
    public Map<String, String> getContext(Object event) {
        throw new NotImplementedException();
    }
    
}
