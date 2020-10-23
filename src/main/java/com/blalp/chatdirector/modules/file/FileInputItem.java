package com.blalp.chatdirector.modules.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import com.blalp.chatdirector.internalModules.common.PassItem;

public class FileInputItem extends PassItem implements Runnable {
    public boolean stop = false;
    private BufferedReader reader;
    public String path;
    public int delay = 500;

    public FileInputItem(String path){
        this.path=path;
    }

    public void run() {
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (!stop) {
            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    this.work(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    @SuppressWarnings("deprecation")
    public void stop() {
        stop = true;
        Thread.currentThread().stop();
    }
}