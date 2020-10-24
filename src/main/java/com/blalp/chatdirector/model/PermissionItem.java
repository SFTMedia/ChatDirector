package com.blalp.chatdirector.model;

import java.util.Map;

public abstract class PermissionItem extends Item implements IPermission {

    @Override
    public String process(String string, Map<String, String> context) {
        if(context.containsKey("PLAYERNAME")){
            context.put("PREFIX", getPrefix(context.get("PLAYERNAME")));
            context.put("SUFFIX", getSuffix(context.get("PLAYERNAME")));
            context.put("GROUP", getGroup(context.get("PLAYERNAME")));
        }
        this.context=context;
        return null;
    }
    
}
