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
