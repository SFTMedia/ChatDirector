package com.blalp.chatdirector.modules.bungee;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import com.blalp.chatdirector.model.ItemDaemon;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import org.bukkit.entity.Player;

public class FromBungeeDaemon extends ItemDaemon {
    public static FromBungeeDaemon instance;

    private FromBungeeDaemon() {
        instance = this;
    }

    public static void trigger(String channel, Player player, byte[] message) {
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subChannel = in.readUTF();
        if (!subChannel.equals("ChatDirector")) {
            // System.err.println("YIKES. Not our message!");
            return;
        }
        short len = in.readShort();
        byte[] msgbytes = new byte[len];
        in.readFully(msgbytes);
        for (FromBungeeItem item : (FromBungeeItem[]) instance.items.toArray()) {
            DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgbytes));
            if (item.channel.equals(in.readUTF())) {
                try {
                    item.work(msgin.readUTF());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
