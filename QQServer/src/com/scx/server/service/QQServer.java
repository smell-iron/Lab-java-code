package com.scx.server.service;

import com.scx.common.Message;
import com.scx.common.MessageType;
import com.scx.common.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class QQServer {
    private ServerSocket ss = null;
    private static HashMap<String, User> validUsers = new HashMap<>();
    static {
        validUsers.put("孙畅煊", new User("孙畅煊", "123"));
        validUsers.put("孙中山", new User("孙中山", "123"));
        validUsers.put("孙坚", new User("孙坚", "123"));
        validUsers.put("孙思邈", new User("孙思邈", "123"));
        validUsers.put("孙膑", new User("孙膑", "123"));
    }
    private boolean checkUser(String userId, String password) {
        if (validUsers.containsKey(userId)) {
            User user = validUsers.get(userId);
            if (user.getPassword().equals(password)) {
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }

    public QQServer() throws IOException, ClassNotFoundException {

        System.out.println("服务端在9999端口监听");
        ss = new ServerSocket(9999);

        while (true) {
            Socket socket = ss.accept();
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            User user = (User) objectInputStream.readObject();
            Message message = new Message();
            if (checkUser(user.getUserId(), user.getPassword())) {
                message.setMesType(MessageType.MESSAGE_LOGIN_SUCCEED);
                objectOutputStream.writeObject(message);
                ServerConnectClientThread serverConnectClientThread = new ServerConnectClientThread(socket, user.getUserId());
                serverConnectClientThread.start();
                ManageClientThreads.addClientThread(user.getUserId(), serverConnectClientThread);
            } else {
                message.setMesType(MessageType.MESSAGE_LOGIN_FAIL);
                objectOutputStream.writeObject(message);
                socket.close();
            }
        }
    }
}
