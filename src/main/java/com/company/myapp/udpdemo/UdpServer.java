package com.company.myapp.udpdemo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpServer {
    public static void main(String[] args) throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket(9999);
        InetAddress inetAddress = InetAddress.getLocalHost();

        byte[] bytes = new byte[1024];
        DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length);
        datagramSocket.receive(datagramPacket);
        String str = new String(datagramPacket.getData());
        int num = Integer.parseInt(str.trim());
        System.out.println("num:" + num);

        int result = num * num;
        byte[] bytes1 = (result + "").getBytes();
        DatagramPacket datagramPacket1 = new DatagramPacket(bytes1, bytes1.length, inetAddress, datagramPacket.getPort());
        datagramSocket.send(datagramPacket1);
    }
}
