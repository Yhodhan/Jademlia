package org.garuda.kademlia;

import java.net.InetAddress;

public class Main {

    public static void main(String[] args) throws Exception {

        Node node = new Node();
        node.init();

        InetAddress addr = InetAddress.getByName("dht.transmissionbt.com");
        int port = 6881;

        node.ping(addr, port);
    }
}