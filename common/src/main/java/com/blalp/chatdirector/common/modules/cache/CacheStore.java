package com.blalp.chatdirector.common.modules.cache;

import java.util.LinkedHashMap;

public class CacheStore {
    private static LinkedHashMap<String, String> data = new LinkedHashMap<>();

    public static String getValue(String key) {
        if (data.containsKey(key)) {
            return data.get(key);
        }
        return "";
    }

    public static void setValue(String key, String value) {
        data.put(key, value);
    }

    public static boolean containsKey(String key) {
        return data.containsKey(key);
    }

    public static void shred() {
        data = new LinkedHashMap<>();
    }
}
