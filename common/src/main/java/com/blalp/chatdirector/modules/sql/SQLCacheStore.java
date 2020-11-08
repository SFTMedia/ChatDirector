package com.blalp.chatdirector.modules.sql;

import java.util.LinkedHashMap;

public class SQLCacheStore {
    private static LinkedHashMap<String,String> data = new LinkedHashMap<>();

    private static String getKey(String connection,String table,String name,String key){
        return connection+"/"+table+"/"+name+"/"+key;
    }
    public static String getValue(String connection,String table,String name,String key){
        if(data.containsKey(getKey(connection, table, name, key))){
            return data.get(getKey(connection, table, name, key));
        }
        return "";
    }
    public static void setValue(String connection,String table,String name,String key,String value){
        data.put(getKey(connection, table, name, key), value);
    }
	public static void containsKey() {
	}
	public static boolean containsKey(String connection, String table, String name, String key) {
		return data.containsKey(getKey(connection, table, name, key));
	}
    public static void removeValue(String connection,String table,String name,String key){
        data.remove(getKey(connection, table, name, key), data.get(getKey(connection, table, name, key)));
    }
}
