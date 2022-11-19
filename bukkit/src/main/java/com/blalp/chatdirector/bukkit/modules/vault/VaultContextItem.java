package com.blalp.chatdirector.bukkit.modules.vault;

import java.util.logging.Level;

import com.blalp.chatdirector.core.ChatDirector;
import com.blalp.chatdirector.minecraft.utils.PermissionItem;
import com.blalp.chatdirector.core.model.Context;

import org.bukkit.Bukkit;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategies.KebabCaseStrategy.class)
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class VaultContextItem extends PermissionItem {

    @SuppressWarnings("deprecation")
    @Override
    public String getPrefix(String playerName) {
        return ((VaultModule) ChatDirector.getConfig().getModule(VaultModule.class)).chat
                .getPlayerPrefix(Bukkit.getWorlds().get(0).getName(), Bukkit.getOfflinePlayer(playerName));
    }

    @SuppressWarnings("deprecation")
    @Override
    public String getSuffix(String playerName) {
        return ((VaultModule) ChatDirector.getConfig().getModule(VaultModule.class)).chat
                .getPlayerSuffix(Bukkit.getWorlds().get(0).getName(), Bukkit.getOfflinePlayer(playerName));
    }

    @SuppressWarnings("deprecation")
    @Override
    public String getGroup(String playerName) {
        return ((VaultModule) ChatDirector.getConfig().getModule(VaultModule.class)).chat
                .getPlayerGroups(Bukkit.getWorlds().get(0).getName(), Bukkit.getOfflinePlayer(playerName))[0];
    }

    @SuppressWarnings("deprecation")
    @Override
    public Context process(Context context) {
        Context output = super.process(context);
        try {
            if (output.containsKey("PLAYER_NAME")) {
                output.put("PLAYER_BALANCE",
                        Double.toString(((VaultModule) ChatDirector.getConfig().getModule(VaultModule.class)).economy
                                .getBalance(Bukkit.getOfflinePlayer(context.get("PLAYER_NAME")))));
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            ChatDirector.getLogger().log(Level.INFO,
                    "The above error probably just means you dont have an econ plugin. dw.");
        }
        return output;
    }

    @Override
    public boolean isValid() {
        return true;
    }
}