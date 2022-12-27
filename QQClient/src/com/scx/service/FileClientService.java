package com.scx.service;

import com.scx.common.Message;
import com.scx.common.MessageType;

import java.io.*;

public class FileClientService {
    public void sendFileToOne(String src, String dest, String sender, String receiver) throws IOException {
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_FILE_MES);
        message.setSrc(src);
        message.setDest(dest);
        message.setSender(sender);
        message.setReceive(receiver);

        byte[] fileBytes = new byte[(int) new File(src).length()];
        FileInputStream fileInputStream = new FileInputStream(src);
        fileInputStream.read(fileBytes);
        message.setFileBytes(fileBytes);
        fileInputStream.close();

        System.out.println(sender + "从" + src + "给" + receiver + "的" + dest + "发送了文件到");

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(sender).getSocket().getOutputStream());
        objectOutputStream.writeObject(message);
    }
}
