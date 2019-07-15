package com.company.myapp.socket;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        System.out.println("socket server服务");
        try {
            ServerSocket serverSocket = new ServerSocket(4999);
            System.out.println("服务器启动！");
            while (true) {
                //获取一个套接字（阻塞）
                final Socket socket = serverSocket.accept();
                System.out.println("来个一个新客户端！");
                //业务处理
                handler(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取数据
     *
     * @param socket
     * @throws Exception
     */
    public static void handler(Socket socket) {
        try {
            byte[] bytes = new byte[1024];
            InputStream inputStream = socket.getInputStream();

            while (true) {
                //读取数据（阻塞）
                int read = inputStream.read(bytes);
                if (read != -1) {
                    System.out.println(new String(bytes, 0, read));
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                System.out.println("socket关闭");
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
