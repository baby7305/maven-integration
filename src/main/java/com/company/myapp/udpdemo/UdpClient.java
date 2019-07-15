package com.company.myapp.udpdemo;

import java.io.IOException;
import java.net.*;

public class UdpClient {
    public static void main(String[] args) throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket();
        InetAddress inetAddress = InetAddress.getLocalHost();

        int i = 5;
        byte[] bytes = (i + "").getBytes();
        DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length, inetAddress, 9999);
        datagramSocket.send(datagramPacket);

        byte[] bytes1 = new byte[1024];
        DatagramPacket datagramPacket1 = new DatagramPacket(bytes1, bytes1.length);
        datagramSocket.receive(datagramPacket1);
        String result = new String(datagramPacket1.getData());
        System.out.println("result is:" + result);
    }
}
