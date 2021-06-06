package com.blalp.chatdirector.core.configuration;

import java.util.logging.Level;

import com.blalp.chatdirector.core.ChatDirector;
import com.blalp.chatdirector.core.model.Context;
import com.blalp.chatdirector.core.model.IItem;

import lombok.Setter;

public class ChainWorker implements Runnable {
    private Chain chain;
    private @Setter Context context;
    private @Setter IItem item;

    public ChainWorker(Chain chain){
        this.chain=chain;
    }
    public ChainWorker(Chain chain,Context context){
        this(chain);
        this.context=context;
    }
    public ChainWorker(Chain chain,IItem item){
        this(chain);
        this.item=item;
    }
    public ChainWorker(Chain chain,IItem item,Context context){
        this(chain,item);
        this.context=context;
    }

    @Override
    public void run() {
        runAt(item, context);
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

    /**
     * This Starts execution at an item.
     * 
     * @param item
     * @param context
     * @return Modified Contexts
     */
    public Context runAt(IItem item, Context context) {
        int index = chain.getItems().indexOf(item);
        if (index == -1) {
            ChatDirector.getLogger().log(Level.SEVERE, item + " not found in this " + chain + " chain.");
            return new Context().halt();
        }
        return runAt(index, context);
    }

    /**
     * This Starts execution at an index.
     * 
     * @param indexOf
     * @return Modified Contexts
     */
    private Context runAt(int indexOf, Context context) {
        Context output;
        for (int i = indexOf; i < chain.getItems().size(); i++) {
            if (ChatDirector.getConfig().debug) {
                ChatDirector.getLogger().log(Level.INFO, "Starting process of " + chain.getItems().get(i));
                ChatDirector.getLogger().log(Level.INFO, context.toString());
            }
            output = chain.getItems().get(i).process(context);
            if (ChatDirector.getConfig().debug) {
                ChatDirector.getLogger().log(Level.INFO,
                        "Ended process of " + chain.getItems().get(i) + " with changed context " + output.toString());
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

}
