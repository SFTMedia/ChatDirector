package com.blalp.chatdirector.bukkit.modules.bukkit;

import com.blalp.chatdirector.core.ChatDirector;
import com.blalp.chatdirector.core.modules.common.PassItem;
import com.blalp.chatdirector.core.utils.ItemDaemon;
import com.blalp.chatdirector.core.utils.ValidationUtils;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategies.KebabCaseStrategy.class)
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BukkitCommandItem extends PassItem {
    String command;
    String permission;

    public BukkitCommandItem(String name, String permission) {
        this.command = name;
        this.permission = permission;
        ((ItemDaemon) ChatDirector.getConfigStaging().getOrCreateDaemon(BukkitCommandDaemon.class)).addItem(this);
    }

    @Override
    public boolean isValid() {
        return ValidationUtils.hasContent(command, permission);
    }
}
