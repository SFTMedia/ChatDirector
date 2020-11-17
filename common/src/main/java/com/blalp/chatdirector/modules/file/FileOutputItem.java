package com.blalp.chatdirector.modules.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.IItem;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=false)
public class FileOutputItem implements IItem {
    String path;
    int delay = 500;
    private BufferedWriter writer;

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