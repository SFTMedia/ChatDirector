package com.blalp.chatdirector.model;

public interface IItem extends IValid {
    /**
     * Processes an item
     * @param context
     * @return A valid string
     */
    public Context process(Context context);
}