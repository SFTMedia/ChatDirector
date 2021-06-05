package com.blalp.chatdirector.bukkit.modules.vault;

import java.util.Arrays;
import java.util.List;

import com.blalp.chatdirector.core.ChatDirector;
import com.blalp.chatdirector.core.model.Context;
import com.blalp.chatdirector.core.model.IItem;
import com.blalp.chatdirector.core.model.IModule;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class VaultModule implements IModule {
    public Permission permission = null;
    public Economy economy = null;
    public Chat chat = null;

    private boolean setupPermissions() {
        if (Bukkit.getServer() == null) {
            return false;
        }
        RegisteredServiceProvider<Permission> permissionProvider = Bukkit.getServer().getServicesManager()
                .getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null) {
            permission = permissionProvider.getProvider();
        }
        return (permission != null);
    }

    private boolean setupChat() {
        if (Bukkit.getServer() == null) {
            return false;
        }
        RegisteredServiceProvider<Chat> chatProvider = Bukkit.getServer().getServicesManager()
                .getRegistration(net.milkbowl.vault.chat.Chat.class);
        if (chatProvider != null) {
            chat = chatProvider.getProvider();
        }

        return (chat != null);
    }

    private boolean setupEconomy() {
        if (Bukkit.getServer() == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServer().getServicesManager()
                .getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }

    @Override
    public boolean load() {
        try {
            Class.forName("org.bukkit.Bukkit");
        } catch (ClassNotFoundException e) {
            return true;
        }
        if(!setupChat()){
            if(ChatDirector.getInstance().isDebug()){
                ChatDirector.getLogger().warning("Vault Chat failed to load");
            }
        }
        if(!setupEconomy()){
            if(ChatDirector.getInstance().isDebug()){
                ChatDirector.getLogger().warning("Vault Chat failed to load");
            }
        }
        if(!setupPermissions()){
            if(ChatDirector.getInstance().isDebug()){
                ChatDirector.getLogger().warning("Vault Chat failed to load");
            }
        }
        return true;
    }

    @Override
    public boolean unload() {
        chat = null;
        permission = null;
        economy = null;
        return true;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public List<String> getItemNames() {
        return Arrays.asList("vault-context");
    }

    @Override
    public Context getContext(Object object) {
        return new Context();
    }

    @Override
    public Class<? extends IItem> getItemClass(String type) {
        switch (type) {
        case "vault-context":
            return VaultContextItem.class;
        default:
            return null;
        }
    }
}