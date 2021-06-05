package com.blalp.chatdirector.common.modules.file;

import java.io.File;

import com.blalp.chatdirector.core.ChatDirector;
import com.blalp.chatdirector.core.modules.common.PassItem;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@Data
@EqualsAndHashCode(callSuper = true)
public class FileInputItem extends PassItem {
    String path;
    boolean create = false;
    int delay = 500;

    public FileInputItem() {
        ((FileInputDaemon) ChatDirector.getConfigStaging().getOrCreateDaemon(FileInputDaemon.class)).addItem(this);
    }

    @Override
    public boolean isValid() {
        return path != null && new File(path).exists();
    }
}