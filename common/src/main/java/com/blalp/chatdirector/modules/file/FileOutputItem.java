package com.blalp.chatdirector.modules.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;

import com.blalp.chatdirector.model.Item;

public class FileOutputItem extends Item {
    Thread thread;
    String path;
    int delay=500;
    private BufferedWriter writer;

    public FileOutputItem(String path) {
        this.path=path;
    }
    
    @Override
    public String process(String string, Map<String,String> context) {
        try {
            if (writer == null) {
                writer = new BufferedWriter(
                        new OutputStreamWriter(new FileOutputStream(new File(path))));
            }
            writer.write(string);
            writer.newLine();
            writer.flush();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        return string;
    }

}