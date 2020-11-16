package com.blalp.chatdirector.utils;

import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.model.IPermission;

public abstract class PermissionItem implements IPermission, IItem {

    @Override
    public Context process(Context context) {
        Context output = new Context();
        if(context.containsKey("PLAYER_NAME")){
            output.put("PLAYER_PREFIX", getPrefix(context.get("PLAYER_NAME")));
            output.put("PLAYER_SUFFIX", getSuffix(context.get("PLAYER_NAME")));
            output.put("PLAYER_GROUP", getGroup(context.get("PLAYER_NAME")));
        }
        return output;
    }
    
}
