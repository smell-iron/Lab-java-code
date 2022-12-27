package com.scx.server.service;

import com.scx.common.Message;
import com.scx.common.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class ServerConnectClientThread extends Thread {
    private Socket socket;
    private String userId;

    public ServerConnectClientThread(Socket socket, String userId) {
        this.socket = socket;
        this.userId = userId;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public void run() {
        System.out.println("服务端与客户端" + userId + "通信");
        while (true) {
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) objectInputStream.readObject();
                if (message.getMesType().equals(MessageType.MESSAGE_GET_ONLINE_FRIENDS)) {
                    System.out.println(message.getSender() + "要在线用户列表");
                    String onlineFriends = ManageClientThreads.getOnlineFriends();
                    Message message1 = new Message();
                    message1.setMesType(MessageType.MESSAGE_RET_ONLINE_FRIENDS);
                    message1.setContent(onlineFriends);
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    objectOutputStream.writeObject(message1);
                } else if (message.getMesType().equals(MessageType.MESSAGE_CLIENT_EXIT)) {
                    System.out.println(userId + "退出系统");
                    Message message1 = new Message();
                    message1.setMesType(MessageType.MESSAGE_CLIENT_EXIT);
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(ManageClientThreads.getClientThread(userId).socket.getOutputStream());
                    objectOutputStream.writeObject(message1);
                    ManageClientThreads.removeClientThread(userId);
                    socket.close();
                    break;
                } else if (message.getMesType().equals(MessageType.MESSAGE_COMM_MES)) {
                    System.out.println(message.getSender() + "给" + message.getReceive() + "发了一条消息");
                    Message message1 = new Message();
                    if (ManageClientThreads.getClientThread(message.getReceive()) != null) {
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(ManageClientThreads.getClientThread(message.getReceive()).getSocket().getOutputStream());
                        objectOutputStream.writeObject(message);
                    } else { // 对方不在线，告知无法发送
                        message.setContent("我现在不在线，请回头再联系");
                    }
                } else if (message.getMesType().equals(MessageType.MESSAGE_TO_ALL_MES)){
                    System.out.println(message.getSender() + "群发了一条消息");
                    List<ServerConnectClientThread> allOnlineClientThread = ManageClientThreads.getAllOnlineClientThread();
                    for (int i = 0; i < allOnlineClientThread.size(); i++) {
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(allOnlineClientThread.get(i).getSocket().getOutputStream());
                        objectOutputStream.writeObject(message);
                    }
                } else if (message.getMesType().equals(MessageType.MESSAGE_FILE_MES)) {
                    if (ManageClientThreads.getClientThread(message.getReceive()) != null) {
                        System.out.println(message.getSender() + "给" + message.getReceive() + "发送了一个文件");
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(ManageClientThreads.getClientThread(message.getReceive()).getSocket().getOutputStream());
                        objectOutputStream.writeObject(message);
                    } else {
                        System.out.println(message.getReceive() + "没登录，无法发送文件");
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
