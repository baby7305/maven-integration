package com.company.myapp.socket;

import java.io.IOException;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        System.out.println("socket client服务");
        try {
            Socket socket = new Socket("localhost", 4999);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
