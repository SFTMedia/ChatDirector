package com.blalp.chatdirector.modules.luckperms;

import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.utils.PermissionItem;

import net.luckperms.api.LuckPermsProvider;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
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
