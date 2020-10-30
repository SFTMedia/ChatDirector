package com.blalp.chatdirector.modules.file;

import java.util.ArrayList;

import com.blalp.chatdirector.model.ItemDaemon;

public class FileInputDaemon extends ItemDaemon {
    public static FileInputDaemon instance;
    private ArrayList<FileInputItemWorker> runners = new ArrayList<>();
    public FileInputDaemon(){
        instance=this;
    }
    @Override
    public void load() {
        for (FileInputItem item : items.toArray(new FileInputItem[]{})) {
            FileInputItemWorker worker = new FileInputItemWorker(item);
            Thread thread = new Thread(worker);
            thread.start();
            runners.add(worker);
        }
    }

    @Override
    public void unload() {
        for (FileInputItemWorker runner : runners) {
            try {
                runner.stop();
            } catch (ThreadDeath e) {
                // System.out.println("Safe to ignore stacktrace.");
            }
        }
        runners = new ArrayList<>();
    }
}