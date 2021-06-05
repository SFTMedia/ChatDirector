package com.blalp.chatdirector.minecraft.utils;

import com.blalp.chatdirector.core.model.Context;
import com.blalp.chatdirector.core.model.IItem;
import com.blalp.chatdirector.core.model.IPermission;

public abstract class PermissionItem implements IPermission, IItem {

    @Override
    public Context process(Context context) {
        Context output = new Context();
        if (context.containsKey("PLAYER_NAME")) {
            output.put("PLAYER_PREFIX", getPrefix(context.get("PLAYER_NAME")));
            output.put("PLAYER_SUFFIX", getSuffix(context.get("PLAYER_NAME")));
            output.put("PLAYER_GROUP", getGroup(context.get("PLAYER_NAME")));
        }
        return output;
    }

}
