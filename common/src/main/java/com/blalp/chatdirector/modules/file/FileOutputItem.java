package com.blalp.chatdirector.modules.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.IItem;

public class FileOutputItem implements IItem {
    String path;
    int delay = 500;
    private BufferedWriter writer;

    public FileOutputItem(String path) {
        this.path = path;
    }

    @Override
    public boolean isValid() {
        return path!=null&&new File(path).exists();
    }

    @Override
    public Context process(Context context) {
        try {
            if (writer == null) {
                writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(path))));
            }
            writer.write(context.getCurrent());
            writer.newLine();
            writer.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Context();
    }

}