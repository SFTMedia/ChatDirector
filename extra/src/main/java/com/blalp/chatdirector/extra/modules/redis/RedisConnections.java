fpackage com.blalp.chatdirector.extra.modules.redis;

import java.util.HashMap;
import java.util.logging.Level;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.IDaemon;
import com.blalp.chatdirector.model.IItem;

public class RedisConnections extends HashMap<String, RedisConnection> implements IDaemon {

    @Override
    public boolean load() {
        for (Entry<String, RedisConnection> connection : this.entrySet()) {
            if (!connection.getValue().load()) {
                ChatDirector.getLogger().log(Level.SEVERE, connection + " failed to load.");
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean unload() {
        for (RedisConnection connection : this.values()) {
            if (!connection.unload()) {
                ChatDirector.getLogger().log(Level.WARNING, connection + " failed to unload.");
            }
        }
        return true;
    }

    @Override
    public void addItem(IItem item) {
        try {
            throw new Exception("addItem not implemented");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
