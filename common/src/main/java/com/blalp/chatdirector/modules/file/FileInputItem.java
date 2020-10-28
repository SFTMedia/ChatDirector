package com.blalp.chatdirector.modules.file;

import com.blalp.chatdirector.modules.common.PassItem;

public class FileInputItem extends PassItem {
    public String path;
    public int delay = 500;

    public FileInputItem(String path){
        this.path=path;
    }
}