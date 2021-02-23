package com.blalp.chatdirector.common.modules.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.logging.Level;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.IItem;
import lombok.Data;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@NoArgsConstructor
@Data
public class FileOutputItem implements IItem {
    String path;
    boolean create = false;
    int delay = 500;
    private BufferedWriter writer;

    @Override
    public boolean isValid() {
        return path != null && new File(path).exists();
    }

    @Override
    public Context process(Context context) {
        if (create) {
            try {
                if (!new File(path).createNewFile()) {
                    if (ChatDirector.isDebug()) {
                        ChatDirector.logger.log(Level.WARNING, path + " already exists");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                ChatDirector.logger.log(Level.SEVERE, "Could not create file " + path);
                return new Context();
            }
        }
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