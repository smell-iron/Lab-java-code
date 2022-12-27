package com.scx.view;

import com.scx.service.FileClientService;
import com.scx.service.MessageClientService;
import com.scx.service.UserClientService;
import com.scx.utils.Utility;

import java.io.IOException;

public class QQView {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        QQView qqView = new QQView();
        qqView.mainMenu();
        System.out.println("client退出");
    }

    private boolean loop = true;
    private String key = "";
    private UserClientService userClientService = new UserClientService();
    private MessageClientService messageClientService = new MessageClientService();
    private FileClientService fileClientService = new FileClientService();

    private void mainMenu() throws IOException, ClassNotFoundException {
        while (loop) {
            System.out.println("========welcome=========");
            System.out.println("\tenter 1 => Login");
            System.out.println("\tenter 9 => exit");
            key = Utility.readString(1);
            switch (key) {
                case "1":
                    System.out.println("请输入用户名");
                    String userId = Utility.readString(50);
                    System.out.println("请输入密  码");
                    String password = Utility.readString(50);
                    if (userClientService.checkUser(userId, password)) {
                        System.out.println("=========" + userId + "欢迎" + "==========");
                        while (loop) {
                            System.out.println("========当前用户：" + userId + "=========");
                            System.out.println("1显示在线用户列表");
                            System.out.println("2群发消息");
                            System.out.println("3私聊消息");
                            System.out.println("4发送文件");
                            System.out.println("9退出系统");
                            key = Utility.readString(1);
                            switch (key) {
                                case "1":
                                    userClientService.onlineFriendList();
                                    System.out.println("1");
                                    break;
                                case "2":
                                    System.out.println("请输入群发消息");
                                    String content = Utility.readString(100);
                                    messageClientService.sendMessageToAll(userId, content);
                                    break;
                                case "3":
                                    System.out.println("请输入想私聊的用户名");
                                    String receive = Utility.readString(50);
                                    System.out.println("请输入要发送的内容");
                                    content = Utility.readString(100);
                                    messageClientService.sendMessageToOne(content, userId, receive);
                                    break;
                                case "4":
                                    System.out.println("请输入要发送的文件地址");
                                    String src = Utility.readString(100);
                                    System.out.println("请输入对方用户电脑的目标地址");
                                    String dest = Utility.readString(100);
                                    System.out.println("请输入要发送的用户名");
                                    String receiver = Utility.readString(50);
                                    fileClientService.sendFileToOne(src,dest,userId, receiver);
                                    break;
                                case "9":
                                    loop = false;
                                    userClientService.logout();
                                    break;

                            }
                        }
                    } else {
                        System.out.println("======登录失败=======");
                    }
                    break;
                case "9":
                    loop = false;
                    userClientService.logout();
                    break;
            }
        }
    }
}
