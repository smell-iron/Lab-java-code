package com.scx.common;

import java.io.Serializable;

/**
 * 通信消息对象
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    private String sender;
    private String receive;
    private String content;
    private String sendTime;
    private String mesType;
    // 和文件相关的类成员
    private byte[] fileBytes;
    private int fileLen = 0;
    private String src;
    private String dest;

    public byte[] getFileBytes() {
        return fileBytes;
    }

    public void setFileBytes(byte[] fileBytes) {
        this.fileBytes = fileBytes;
    }

    public int getFileLen() {
        return fileLen;
    }

    public void setFileLen(int fileLen) {
        this.fileLen = fileLen;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getMesType() {
        return mesType;
    }

    public void setMesType(String mesType) {
        this.mesType = mesType;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceive() {
        return receive;
    }

    public void setReceive(String receive) {
        this.receive = receive;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public Message(String sender, String receive, String content, String sendTime) {
        this.sender = sender;
        this.receive = receive;
        this.content = content;
        this.sendTime = sendTime;
    }

    public Message() {
    }
}
