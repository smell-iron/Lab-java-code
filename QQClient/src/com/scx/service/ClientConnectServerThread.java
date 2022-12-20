package com.scx.service;

import com.scx.common.Message;
import com.scx.common.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StringReader;
import java.net.Socket;

public class ClientConnectServerThread extends Thread {
    private Socket socket;

    @Override
    public void run() {
        while (true) {
            System.out.println("客户端线程，等待从服务端读取消息");
            try {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) ois.readObject();
                if (message.getMesType().equals(MessageType.MESSAGE_RET_ONLINE_FRIENDS)) {
                    String[] online_users = message.getContent().split(" ");
                    System.out.println("========在线用户列表如下==========");
                    for (String s : online_users) {
                        System.out.println(s);
                    }
                } else if (message.getMesType().equals(MessageType.MESSAGE_CLIENT_EXIT)) {
                    break;
                } else if (message.getMesType().equals(MessageType.MESSAGE_COMM_MES)) {
                    System.out.println(message.getSender() + "：" + message.getContent());
                } else {
                    System.out.println("暂时不处理");
                }
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public ClientConnectServerThread(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
