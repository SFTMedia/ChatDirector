package com.blalp.chatdirector.model;

import java.util.Map;

public abstract class PermissionItem extends Item implements IPermission {

    @Override
    public String process(String string, Map<String, String> context) {
        if(context.containsKey("PLAYER_NAME")){
            context.put("PREFIX", getPrefix(context.get("PLAYER_NAME")));
            context.put("SUFFIX", getSuffix(context.get("PLAYER_NAME")));
            context.put("GROUP", getGroup(context.get("PLAYER_NAME")));
        }
        this.context=context;
        return null;
    }
    
}
