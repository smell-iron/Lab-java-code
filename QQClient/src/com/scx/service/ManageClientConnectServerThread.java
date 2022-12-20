package com.scx.service;

import java.util.HashMap;

public class ManageClientConnectServerThread {
    private static HashMap<String, ClientConnectServerThread> hashMap = new HashMap<>();

    public static void addClientConnectServerThread(String userId, ClientConnectServerThread thread) {
        hashMap.put(userId, thread);
    }

    public static ClientConnectServerThread getClientConnectServerThread(String userId) {
        return hashMap.get(userId);
    }
}
