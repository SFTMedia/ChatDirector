package com.blalp.chatdirector.core.utils;

import com.blalp.chatdirector.core.configuration.Chain;

public class ValidationUtils {
    public static boolean hasContent(String... strings) {
        for (String s : strings) {
            if (s == null || s.trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public static boolean hasContent(Chain... chains) {
        for (Chain chain : chains) {
            if (chain == null || !chain.isValid()) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotNull(Object... objects) {
        for (Object obj : objects) {
            if (obj == null) {
                return false;
            }
        }
        return true;
    }

    public static boolean anyOf(boolean... checks) {
        for (boolean check : checks) {
            if (check) {
                return true;
            }
        }
        return false;
    }
}
