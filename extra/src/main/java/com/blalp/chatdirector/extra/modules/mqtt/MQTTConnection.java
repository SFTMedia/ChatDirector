package com.blalp.chatdirector.extra.modules.mqtt;

import java.net.URISyntaxException;

import com.blalp.chatdirector.core.model.ILoadable;

import org.fusesource.mqtt.client.BlockingConnection;
import org.fusesource.mqtt.client.FutureConnection;
import org.fusesource.mqtt.client.MQTT;

public class MQTTConnection implements ILoadable {

    // https://github.com/fusesource/mqtt-client
    // tcp://localhost:1883
    /*
     * ssl:// - Use the JVM default version of the SSL algorithm. sslv*:// - Use a
     * specific SSL version where * is a version supported by your JVM. Example:
     * sslv3 tls:// - Use the JVM default version of the TLS algorithm. tlsv*:// -
     * Use a specific TLS version where * is a version supported by your JVM.
     * Example: tlsv1.1
     */
    String connectionString;
    MQTT mqtt;
    BlockingConnection blockingConnection;
    FutureConnection futureConnection;

    public MQTTConnection(String connectionString) {
        this.connectionString = connectionString;
    }

    @Override
    public boolean load() {
        MQTT mqtt = new MQTT();
        try {
            mqtt.setHost(connectionString);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return false;
        }
        blockingConnection = mqtt.blockingConnection();
        try {
            blockingConnection.connect();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        futureConnection = mqtt.futureConnection();
        try {
            futureConnection.connect().await();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean unload() {
        try {
            futureConnection.disconnect().await();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        try {
            blockingConnection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public BlockingConnection getBlockingConnection() {
        return blockingConnection;
    }

    public FutureConnection getFutureConnection() {
        return futureConnection;
    }
}
