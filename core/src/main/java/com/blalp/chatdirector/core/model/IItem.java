package com.blalp.chatdirector.core.model;

public interface IItem extends IValid {
    /**
     * Processes an item
     * 
     * @param context
     * @return A valid string
     */
    public Context process(Context context);
}