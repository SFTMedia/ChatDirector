package com.blalp.chatdirector.modules.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Stack;

import com.blalp.chatdirector.model.Item;

public class FileOutputItem extends Item implements Runnable {
    private BufferedWriter writer;
    private Stack<String> buf = new Stack<>();
    String path;
    int delay=500;

    public FileOutputItem() {
        new Thread(this).start();
    }


    @Override
    public String process(String string) {
        buf.push(string);
        return string;
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (writer == null) {
                    writer = new BufferedWriter(
                            new OutputStreamWriter(new FileOutputStream(new File(path))));
                }
                while (!buf.isEmpty()) {
                    writer.write(buf.pop());
                    writer.newLine();
                }
                writer.flush();
            } catch (IOException e) {
                try {
                    writer = new BufferedWriter(
                            new OutputStreamWriter(new FileOutputStream(new File(path))));
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
                e.printStackTrace();
            }
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}