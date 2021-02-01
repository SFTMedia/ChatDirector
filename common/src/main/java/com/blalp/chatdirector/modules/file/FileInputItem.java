package com.blalp.chatdirector.modules.file;

import java.io.File;

import com.blalp.chatdirector.modules.common.PassItem;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@Data
@EqualsAndHashCode(callSuper = true)
public class FileInputItem extends PassItem {
    public String path;
    boolean create = false;
    public int delay = 500;

    public FileInputItem() {
        if (FileInputDaemon.instance == null) {
            FileInputDaemon.instance = new FileInputDaemon();
        }
        FileInputDaemon.instance.addItem(this);
    }

    @Override
    public boolean isValid() {
        return path != null && new File(path).exists();
    }
}