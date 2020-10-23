package com.blalp.chatdirector.modules.file;

import java.util.ArrayList;

import com.blalp.chatdirector.model.ItemDaemon;

public class FileInputDaemon extends ItemDaemon {
    public static FileInputDaemon instance = new FileInputDaemon();
    private ArrayList<FileInputItem> runners = new ArrayList<>();

    @Override
    public void load() {
        for (FileInputItem item : (FileInputItem[])items.toArray()) {
            Thread thread = new Thread(item);
            thread.start();
            runners.add(item);
        }
    }

    @Override
    public void unload() {
        for (FileInputItem runner : runners) {
            try {
                runner.stop();
            } catch (ThreadDeath e) {
                // System.out.println("Safe to ignore stacktrace.");
            }
        }
        runners = new ArrayList<>();
    }
}