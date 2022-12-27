package com.scx.service;

import com.scx.common.Message;
import com.scx.common.MessageType;
import com.scx.common.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

public class MessageClientService {
    public void sendMessageToOne(String content, String sender, String receive) throws IOException {
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_COMM_MES);
        message.setSender(sender);
        message.setReceive(receive);
        message.setContent(content);
        message.setSendTime(new Date().toString());
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(sender).getSocket().getOutputStream());
        objectOutputStream.writeObject(message);
        System.out.println("私聊已发送");
    }

    public void sendMessageToAll(String userId, String content) throws IOException {
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_TO_ALL_MES);
        message.setSender(userId);
        message.setContent(content);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(userId).getSocket().getOutputStream());
        objectOutputStream.writeObject(message);
        System.out.println("群发已发送");
    }
}
