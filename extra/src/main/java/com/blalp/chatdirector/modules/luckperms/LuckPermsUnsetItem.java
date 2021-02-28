package com.blalp.chatdirector.modules.luckperms;

import java.util.UUID;

import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.modules.common.PassItem;
import com.blalp.chatdirector.utils.ValidationUtils;

import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class LuckPermsUnsetItem extends PassItem {
    String permission;
    boolean value = true;

    public LuckPermsUnsetItem(String permission) {
        this.permission = permission;
    }

    @Override
    public Context process(Context context) {
        // DO NOT RESOLVE CONTEXTS ON THE PERMISSION NODE
        if (context.containsKey("PLAYER_UUID")) {
            User user = LuckPermsProvider.get().getUserManager().getUser(UUID.fromString(context.get("PLAYER_UUID")));
            user.data().remove(Node.builder(permission).value(value).build());
            LuckPermsProvider.get().getUserManager().saveUser(user);
        } else {
            System.err.println("PLAYER_UUID not set");
        }
        return new Context();
    }

    @Override
    public boolean isValid() {
        return ValidationUtils.hasContent(permission);
    }
}
