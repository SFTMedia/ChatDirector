package com.blalp.chatdirector.model;

import java.util.List;
import java.util.logging.Level;

import com.blalp.chatdirector.ChatDirector;

public class Chain implements IValid, Runnable {
    public List<IItem> items;
    int index;
    Context context;
    /**
     * This Starts execution at an item on a new thread.
     * @param item
     * @param context
     */
    public void runAsync(IItem item, Context context) {
        this.index=items.indexOf(item);
        if(index==-1){
            ChatDirector.log(Level.SEVERE, item+" not found in this "+this+" chain.");
            return;
        }
        this.context=context;
        new Thread(this).start();
    }
    /**
     * This Starts execution at an item.
     * @param item
     * @param context
     * @return Modified Contexts
     */
    public Context runAt(IItem item, Context context) {
        index=items.indexOf(item);
        if(index==-1){
            ChatDirector.log(Level.SEVERE, item+" not found in this "+this+" chain.");
            return new Context().halt();
        }
        return runAt(items.indexOf(item), context);
    }
    /**
     * This Starts execution at an index.
     * @param indexOf
     * @return Modified Contexts
     */
    private Context runAt(int indexOf, Context context) {
        Context workingContext = new Context();
        for(int i=indexOf;i<items.size();i++){
            ChatDirector.logDebug("");
            ChatDirector.logDebug("Starting process of "+items.get(i));
            ChatDirector.logDebug(context);
            ChatDirector.logDebug("");
            Context output = items.get(i).process(context);
            ChatDirector.logDebug("");
            ChatDirector.logDebug("Ended process of "+items.get(i));
            ChatDirector.logDebug(output);
            ChatDirector.logDebug("");
            // Setup LAST and CURRENT contexts
            context.put("LAST",context.get("CURRENT"));
            if(output!=null){
                workingContext.merge(output);
                context.merge(output);
                if(context.isHalt()) {
                    ChatDirector.logDebug("Quitting chain, Halt received. "+this);
                    break;
                }
            } else {
                ChatDirector.logDebug("Quitting chain. "+this);
                break;
            }
        }
        return workingContext;
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
     * @param context
     * @return Modified Contexts
     */
    public Context run(Context context) {
        return runAt(0, context);
    }

    @Override
    public boolean isValid() {
        for(IItem item:items){
            if(!item.isValid()) {
                ChatDirector.logDebug(item+" is not valid.");
                return false;
            }
        }
        return true;
    }

    @Override
    public void run() {
        runAt(index, context);
    }
}