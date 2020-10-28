package com.blalp.chatdirector.model;

import java.util.Map;

public abstract class PermissionItem extends Item implements IPermission {

    @Override
    public String process(String string, Map<String, String> context) {
        if(context.containsKey("PLAYER_NAME")){
            context.put("PLAYER_PREFIX", getPrefix(context.get("PLAYER_NAME")));
            context.put("PLAYER_SUFFIX", getSuffix(context.get("PLAYER_NAME")));
            context.put("PLAYER_GROUP", getGroup(context.get("PLAYER_NAME")));
        }
        this.context=context;
        return string;
    }
    
}
