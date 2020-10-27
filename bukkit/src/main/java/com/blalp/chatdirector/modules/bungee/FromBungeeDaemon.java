package com.blalp.chatdirector.modules.bungee;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.ItemDaemon;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import org.bukkit.entity.Player;

public class FromBungeeDaemon extends ItemDaemon {
    public static FromBungeeDaemon instance;

    public FromBungeeDaemon() {
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
        Map<String,String> context = new LinkedHashMap<String,String>();
        for (FromBungeeItem item : (FromBungeeItem[]) instance.items.toArray()) {
            DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgbytes));
            if (item.channel.equals(in.readUTF())) {
                try {
                    context = new LinkedHashMap<String,String>();
                    int contextLength = in.readInt();
                    for (int i = 0; i < contextLength; i++) {
                        context.put(in.readUTF(),in.readUTF());
                    }
                    context.putAll(ChatDirector.formatter.getContext(player));
                    item.startWork(msgin.readUTF(),true,context);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
