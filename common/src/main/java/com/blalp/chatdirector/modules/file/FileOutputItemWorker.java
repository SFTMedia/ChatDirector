package com.blalp.chatdirector.modules.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Stack;

public class FileOutputItemWorker implements Runnable {
    FileOutputItem item;
    private BufferedWriter writer;
    public Stack<String> buf = new Stack<>();
    int delay=500;

    public FileOutputItemWorker(FileOutputItem item) {
        this.item=item;
    }
    @Override
    public void run() {
        while (true) {
            try {
                if (writer == null) {
                    writer = new BufferedWriter(
                            new OutputStreamWriter(new FileOutputStream(new File(item.path))));
                }
                while (!buf.isEmpty()) {
                    writer.write(buf.pop());
                    writer.newLine();
                }
                writer.flush();
            } catch (IOException e) {
                try {
                    writer = new BufferedWriter(
                            new OutputStreamWriter(new FileOutputStream(new File(item.path))));
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