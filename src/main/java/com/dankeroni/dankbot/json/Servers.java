package com.dankeroni.dankbot.json;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Servers {

    private String cluster;
    private String[] servers, websocket_servers;
    private Random r = new Random();

    public String randomServer(){
        Collections.shuffle(Arrays.asList(servers));

        for(String server: servers){
            if(server.endsWith(String.valueOf(6667)))
                return server;
        }

        return servers[r.nextInt(servers.length)];
    }

}
