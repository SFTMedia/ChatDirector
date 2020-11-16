package com.blalp.chatdirector.modules.file;

import java.io.File;

import com.blalp.chatdirector.modules.common.PassItem;

public class FileInputItem extends PassItem {
    public String path;
    public int delay = 500;

    public FileInputItem(String path){
        this.path=path;
    }

    @Override
    public boolean isValid() {
        return path!=null&&new File(path).exists();
    }
}