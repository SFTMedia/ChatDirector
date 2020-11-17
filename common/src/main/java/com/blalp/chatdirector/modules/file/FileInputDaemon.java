package com.blalp.chatdirector.modules.file;

import java.util.ArrayList;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.utils.ItemDaemon;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileInputDaemon extends ItemDaemon implements Runnable {
    public static FileInputDaemon instance;
    private ArrayList<FileInputDaemon> runners = new ArrayList<>();
    private BufferedReader reader;
    FileInputItem item;

    public FileInputDaemon() {
        instance = this;
    }

    public FileInputDaemon(FileInputItem item) {
        this.item = item;
    }

    @Override
    public boolean load() {
        for (FileInputItem item : items.toArray(new FileInputItem[] {})) {
            FileInputDaemon worker = new FileInputDaemon(item);
            Thread thread = new Thread(worker);
            thread.start();
            runners.add(worker);
        }
        return true;
    }

    @Override
    public boolean unload() {
        for (FileInputDaemon runner : runners) {
            try {
                runner.stop();
            } catch (ThreadDeath e) {
                // System.out.println("Safe to ignore stacktrace.");
            }
        }
        runners = new ArrayList<>();
        return true;
    }

    public void run() {
        Context context;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(item.path))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                ChatDirector.logDebug(line + " read");
                context = new Context(line);
                context.put("FILE_PATH", item.path);
                ChatDirector.run(item, context, true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(item.delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("deprecation")
    public void stop() {
        Thread.currentThread().stop();
    }
}