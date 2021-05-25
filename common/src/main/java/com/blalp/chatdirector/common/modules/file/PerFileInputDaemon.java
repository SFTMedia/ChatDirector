package com.blalp.chatdirector.common.modules.file;

import java.util.logging.Level;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.model.ILoadable;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class PerFileInputDaemon implements Runnable, ILoadable {
    private BufferedReader reader;
    private static Thread thread;
    FileInputItem item;

    public PerFileInputDaemon(IItem item) {
        this.item = (FileInputItem) item;
    }

    @Override
    public boolean load() {
        return true;
    }

    @Override
    public boolean unload() {
        this.stop();
        return true;
    }

    public void run() {
        thread=Thread.currentThread();
        Context context;
        if (item.create) {
            try {
                if (!new File(item.path).createNewFile()) {
                    if (ChatDirector.getInstance().isDebug()) {
                        ChatDirector.getLogger().log(Level.WARNING, item.path + " already exists");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                ChatDirector.getLogger().log(Level.SEVERE, "Could not create file " + item.path);
                return;
            }
        }
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(item.path))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                ChatDirector.getLogger().log(Level.WARNING, line + " read");
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
        thread.stop();
    }
}