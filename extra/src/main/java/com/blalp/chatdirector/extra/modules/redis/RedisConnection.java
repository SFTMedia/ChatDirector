package com.blalp.chatdirector.extra.modules.redis;

import com.blalp.chatdirector.model.ILoadable;
import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisURI;

import lombok.Data;

@Data
public class RedisConnection implements ILoadable {
    private String connectionString;
    private com.lambdaworks.redis.RedisConnection<String, String> connection;
    private RedisClient redisClient;

    public RedisConnection(String connection) {
        connectionString = connection;
    }

    @Override
    public boolean load() {
        redisClient = new RedisClient(RedisURI.create(connectionString));
        connection = redisClient.connect();
        return true;
    }

    @Override
    public boolean unload() {
        if(connection!=null){
            connection.close();
        }
        if(redisClient!=null){
            redisClient.shutdown();
        }
        return true;
    }

}
