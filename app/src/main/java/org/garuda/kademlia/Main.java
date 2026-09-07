package org.garuda.kademlia;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws Exception {

        Node node = new Node();

        InetAddress addr = InetAddress.getByName("dht.transmissionbt.com");
        int port = 6881;

        node.ping(addr, port);

        byte[] buffer = new byte[4096];
        DatagramPacket response = new DatagramPacket(buffer, buffer.length);
        node.getSocket().setSoTimeout(5000);

        node.getSocket().receive(response);

        byte[] received = Arrays.copyOf(response.getData(), response.getLength());
        System.out.println("Raw reply: " + received);

        Object decoded = Bencoder.decodeMap(received);
        System.out.println("Decoded: " + decoded);
    }
}