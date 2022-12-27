package com.scx.server.service;

import java.util.*;

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

    public static List<ServerConnectClientThread> getAllOnlineClientThread() {
        Set<String> users = hm.keySet();
        ArrayList<ServerConnectClientThread> threads = new ArrayList<>();
        Iterator<String> iterator = users.iterator();
        while (iterator.hasNext()) {
            String userId =  iterator.next();
            ServerConnectClientThread serverConnectClientThread = hm.get(userId);
            threads.add(serverConnectClientThread);
        }
        return threads;
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
