package com.blalp.chatdirector.core.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import com.blalp.chatdirector.core.ChatDirector;
import com.blalp.chatdirector.core.model.Context;
import com.blalp.chatdirector.core.model.IItem;
import com.blalp.chatdirector.core.model.IValid;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@JsonDeserialize(using = ChainDeserializer.class)
@ToString
public class Chain implements IValid {
    List<IItem> items = new ArrayList<>();
    @JsonIgnore
    boolean invalidItem;

    public Chain() {

    }

    public Chain(Iterable<IItem> items) {
        for (IItem item : items) {
            this.items.add(item);
        }
    }

    /**
     * This Starts execution at an item on a new thread.
     * 
     * @param item
     * @param context
     */
    public void runAsync(IItem item, Context context) {
        ChainWorker chainWorker = new ChainWorker(this,item,context);
        new Thread(chainWorker).start();
    }

    /**
     * @param item
     * @return Whether or not items contains item
     */
    public boolean contains(IItem item) {
        return items.contains(item);
    }

    @Override
    public boolean isValid() {
        for (IItem item : items) {
            if (!item.isValid()) {
                ChatDirector.getLogger().log(Level.SEVERE, item + " is not valid.");
                return false;
            }
        }
        if(invalidItem){
            ChatDirector.getLogger().log(Level.SEVERE, "failed to read all items.");
            return false;
        } else {
            return true;
        }
    }

    public void addItem(IItem item) {
        items.add(item);
        ChatDirector.addItem(item, this);
    }

    public List<IItem> getItems() {
        return items;
    }
    public void setInvalidItem(){
        invalidItem=true;
    }

    public Context run(Context context) {
        ChainWorker worker = new ChainWorker(this,context);
        return worker.run(context);
    }

    public Context runAt(IItem item, Context context) {
        ChainWorker worker = new ChainWorker(this,item,context);
        return worker.run(context);
    }

    public void runAsync(Context context) {
        runAsync(items.get(0), context);
    }
}