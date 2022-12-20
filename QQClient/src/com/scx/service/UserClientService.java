package com.scx.service;

import com.scx.common.Message;
import com.scx.common.MessageType;
import com.scx.common.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class UserClientService {
    private User u = new User();
    private Socket socket = null;

    public boolean checkUser(String userId, String password) throws IOException, ClassNotFoundException {
        boolean b = false;
        u.setUserId(userId);
        u.setPassword(password);
        // 发送对象
        socket = new Socket(InetAddress.getByName("127.0.0.1"), 9999);
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(u);
        // 读取返回对象
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        Message message = (Message) ois.readObject();

        if (message.getMesType().equals(MessageType.MESSAGE_LOGIN_SUCCEED)) {
            b = true;
            ClientConnectServerThread ccst = new ClientConnectServerThread(socket);
            ManageClientConnectServerThread.addClientConnectServerThread(userId, ccst);
            ccst.start();
        } else {
            socket.close();
        }
        return b;
    }

    public void onlineFriendList() throws IOException {
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_GET_ONLINE_FRIENDS);
        message.setSender(u.getUserId());


        ObjectOutputStream objectOutputStream =
                new ObjectOutputStream(
                        ManageClientConnectServerThread.getClientConnectServerThread(u.getUserId()).getSocket().getOutputStream());
        objectOutputStream.writeObject(message);
    }

    public void logout() throws IOException {
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_CLIENT_EXIT);
        message.setSender(u.getUserId());
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(u.getUserId()).getSocket().getOutputStream());
        objectOutputStream.writeObject(message);
        System.out.println(u.getUserId() + "退出系统");
        System.exit(0);
    }
}
