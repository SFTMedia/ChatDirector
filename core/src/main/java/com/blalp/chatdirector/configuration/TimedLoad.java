package com.blalp.chatdirector.configuration;

import com.blalp.chatdirector.ChatDirector;

public class TimedLoad implements Runnable {
    private static TimedLoad instance;
    boolean loop = true;

    public TimedLoad() {
        if (instance != null) {
            loop = false;
        } else {
            instance = this;
        }
    }

    @Override
    public void run() {
        System.out.println("Starting Timed load");
        while (loop) {
            System.out.println("Timed load attempting to load");
            try {
                ChatDirector.getInstance().load();
                if (!ChatDirector.hasChains()) {
                    throw new Exception("No CHAINS!");
                }
                System.out.println("Timed load completed.");
                loop = false;
                instance = null;
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                System.out.println("Timed load sleeping");
                Thread.sleep(10000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
    }

}
