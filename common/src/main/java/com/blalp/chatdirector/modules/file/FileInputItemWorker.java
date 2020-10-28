package com.blalp.chatdirector.modules.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
public class FileInputItemWorker implements Runnable {
    public boolean stop = false;
    private BufferedReader reader;
    FileInputItem item;
    public FileInputItemWorker(FileInputItem item){
        this.item=item;
    }
    public void run() {
        HashMap<String,String> context = new HashMap<>();
        context.put("FILE_PATH", item.path);
        context.put("FILE_DELAY", item.path);
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(item.path))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (!stop) {
            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line+" read");
                    item.startWork(line,true,context);
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
    }
    @SuppressWarnings("deprecation")
    public void stop() {
        stop = true;
        Thread.currentThread().stop();
    }
}