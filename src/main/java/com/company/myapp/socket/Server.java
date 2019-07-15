package com.company.myapp.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        System.out.println("socket server服务");
        try {
            ServerSocket serverSocket = new ServerSocket(4999);
            Socket socket = serverSocket.accept();
            System.out.println("client connected");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
