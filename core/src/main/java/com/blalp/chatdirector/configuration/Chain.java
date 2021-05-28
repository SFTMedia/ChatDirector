package com.blalp.chatdirector.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.model.IValid;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@JsonDeserialize(using = ChainDeserializer.class)
@ToString
public class Chain implements IValid, Runnable {
    List<IItem> items = new ArrayList<>();
    int index;
    Context context;
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
        this.index = items.indexOf(item);
        if (index == -1) {
            ChatDirector.getLogger().log(Level.SEVERE, item + " not found in this " + this + " chain.");
            return;
        }
        this.context = context;
        new Thread(this).start();
    }

    /**
     * This Starts execution at an item.
     * 
     * @param item
     * @param context
     * @return Modified Contexts
     */
    public Context runAt(IItem item, Context context) {
        index = items.indexOf(item);
        if (index == -1) {
            ChatDirector.getLogger().log(Level.SEVERE, item + " not found in this " + this + " chain.");
            return new Context().halt();
        }
        return runAt(items.indexOf(item), context);
    }

    /**
     * This Starts execution at an index.
     * 
     * @param indexOf
     * @return Modified Contexts
     */
    private Context runAt(int indexOf, Context context) {
        Context output;
        for (int i = indexOf; i < items.size(); i++) {
            if (ChatDirector.getConfig().debug) {
                ChatDirector.getLogger().log(Level.INFO, "Starting process of " + items.get(i));
                ChatDirector.getLogger().log(Level.INFO, context.toString());
            }
            output = items.get(i).process(context);
            if (ChatDirector.getConfig().debug) {
                ChatDirector.getLogger().log(Level.INFO,
                        "Ended process of " + items.get(i) + " with changed context " + output.toString());
            }
            // Setup LAST and CURRENT contexts
            if (output != null) {
                // Only if CURRENT was changed and not null set LAST
                if (context.get("CURRENT") != null && output.get("CURRENT") != null
                        && !output.getCurrent().equals(context.getCurrent())) {
                    output.put("LAST", context.get("CURRENT"));
                }
                context.merge(output);
                if (context.isHalt()) {
                    if (ChatDirector.getConfig().debug) {
                        ChatDirector.getLogger().log(Level.WARNING, "Quitting chain, Halt received. " + this);
                    }
                    break;
                }
            } else {
                if (ChatDirector.getConfig().debug) {
                    ChatDirector.getLogger().log(Level.WARNING, "Quitting chain. " + this);
                }
                break;
            }
        }
        if (ChatDirector.getConfig().debug) {
            ChatDirector.getLogger().log(Level.INFO, "Ended process of " + this + " with context " + context);
        }
        return context;
    }

    /**
     * @param item
     * @return Whether or not items contains item
     */
    public boolean contains(IItem item) {
        return items.contains(item);
    }

    /**
     * Runs chain from Start
     * 
     * @param context
     * @return Modified Contexts
     */
    public Context run(Context context) {
        Context output = context;
        output.merge(runAt(0, context));
        return output;
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

    @Override
    public void run() {
        runAt(index, context);
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
}