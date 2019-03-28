package com.company.myapp.netty.echo;

public class EchoStart {
    public static void main(String[] args) {
        try {
            new EchoClient().connect("172.29.3.6", 8007);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
