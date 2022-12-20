package com.scx.server.service;

import java.util.HashMap;
import java.util.Set;

public class ManageClientThreads {
    private static HashMap<String, ServerConnectClientThread> hm = new HashMap<>();

    public static void addClientThread(String userId, ServerConnectClientThread thread) {
        hm.put(userId, thread);
    }

    public static void removeClientThread(String userId) {
        hm.remove(userId);
    }

    public static ServerConnectClientThread getClientThread(String userId) {
        return hm.get(userId);
    }

    public static String getOnlineFriends() {
        Set<String> strings = hm.keySet();
        String res = "";
        for (String s : strings) {
            res += s;
            res += " ";
        }
        return res;
    }
}
