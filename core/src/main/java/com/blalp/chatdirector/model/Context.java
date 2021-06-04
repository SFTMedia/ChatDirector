package com.blalp.chatdirector.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Allows us to be more creative about contexts in the future
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Context extends HashMap<String, String> {
    boolean halt = false;
    List<Object> removeKeys = new ArrayList<>();

    public Context() {
    }

    public Context(String current) {
        this.put("CURRENT", current);
    }

    public Context(String current, String last) {
        this(current);
        this.put("LAST", last);
    }

    public Context(Context context) {
        this.merge(context);
    }

    /**
     * @return Value of CURRENT in contexts, or an empty string if CURRENT is not
     *         found
     */
    public String getCurrent() {
        if (this.containsKey("CURRENT")) {
            return this.get("CURRENT");
        } else {
            return "";
        }
    }

    /**
     * @return Value of LAST in contexts, or an empty string if LAST is not found
     */
    public String getLast() {
        if (this.containsKey("LAST")) {
            return this.get("LAST");
        } else {
            return "";
        }
    }

    /**
     * @return If the execution should halt
     */
    public boolean isHalt() {
        return halt;
    }

    /**
     * Merges the specified context into this one
     * 
     * @param context The context to merge
     */
    public void merge(Context context) {
        this.putAll(context);
        this.halt = context.isHalt();
        for (Object key : context.removeKeys) {
            this.remove(key);
        }
    }

    public Context halt() {
        halt = true;
        return this;
    }

    @Override
    public String remove(Object key) {
        removeKeys.add(key);
        return super.remove(key);
    }

    public Context diff(Context other) {
        Context output = new Context();
        for (String key : this.keySet()) {
            if (!other.containsKey(key)) {
                other.removeKeys.add(key);
            } else {
                if (!other.get(key).equals(this.get(key))) {
                    output.put(key, other.get(key));
                }
            }
        }
        if (other.halt || halt) {
            output.halt = true;
        }
        for (Object key : other.removeKeys) {
            if (!this.removeKeys.contains(key)) {
                output.removeKeys.add(key);
            }
        }
        return output;
    }

    public Context getContextAtPath(String string) {
        Context output = new Context();
        for (String key : this.keySet()) {
            if(key.startsWith(string)){
                output.put(key.substring(string.length()), this.get(key));
            }
        }
        return output;
    }
}
