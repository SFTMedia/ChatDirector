package com.blalp.chatdirector.modules.vault;
import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.modules.Module;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;


public class VaultModule extends Module {

    @Override
    public String[] getItemNames() {
        return new String[]{"vault-context"};
    }

    @Override
    public IItem createItem(String type, Object config) {
        switch (type){
            case "vault-context":
                return new VaultContextItem();
            default:
                return null;
        }
    }
    public static Permission permission = null;
    public static Economy economy = null;
    public static Chat chat = null;

    private boolean setupPermissions()
    {
        RegisteredServiceProvider<Permission> permissionProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null) {
            permission = permissionProvider.getProvider();
        }
        return (permission != null);
    }

    private boolean setupChat()
    {
        RegisteredServiceProvider<Chat> chatProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
        if (chatProvider != null) {
            chat = chatProvider.getProvider();
        }

        return (chat != null);
    }

    private boolean setupEconomy()
    {
        RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }
    @Override
    public void load() {
        setupChat();
        setupEconomy();
        setupPermissions();
    }

    @Override
    public void unload() {
        chat=null;
        permission=null;
        economy=null;
    }
}