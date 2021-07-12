package com.blalp.chatdirector.extra.modules.luckperms;

import com.blalp.chatdirector.minecraft.utils.PermissionItem;
import com.blalp.chatdirector.core.model.Context;

import net.luckperms.api.LuckPermsProvider;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.NoArgsConstructor;
import lombok.ToString;

@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class LuckPermsContextItem extends PermissionItem {

    @Override
    public String getPrefix(String playerName) {
        return LuckPermsProvider.get().getUserManager().getUser(playerName).getCachedData().getMetaData().getPrefix();
    }

    @Override
    public String getSuffix(String playerName) {
        return LuckPermsProvider.get().getUserManager().getUser(playerName).getCachedData().getMetaData().getSuffix();
    }

    @Override
    public String getGroup(String playerName) {
        return LuckPermsProvider.get().getUserManager().getUser(playerName).getCachedData().getMetaData()
                .getPrimaryGroup();
    }

    @Override
    public Context process(Context context) {
        Context output = super.process(context);
        output.put("SERVER_LUCKPERMS_NAME", LuckPermsProvider.get().getServerName());
        if (!context.containsKey("SERVER_NAME")) {
            output.put("SERVER_NAME", LuckPermsProvider.get().getServerName());
        }
        return output;
    }

    @Override
    public boolean isValid() {
        return true;
    }

}
