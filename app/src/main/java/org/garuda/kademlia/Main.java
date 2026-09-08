package org.garuda.kademlia;

import java.net.InetAddress;

public class Main {

    public static void main(String[] args) throws Exception {

        Node node = new Node();
        node.init();

        // InetAddress addr = InetAddress.getByName("dht.transmissionbt.com");
        InetAddress addr = InetAddress.getByName("localhost");
        int port = 6882;

        node.ping(addr, port);
    }
}