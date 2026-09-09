package org.garuda.kademlia;

import java.math.BigInteger;
import java.net.InetAddress;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {

        Node node = new Node();
        node.init();

        InetAddress addr = InetAddress.getByName("dht.transmissionbt.com");
        int port = 6881;
        node.ping(addr, port);

        // InetAddress addr = InetAddress.getByName("localhost");
        byte[] bytes = new BigInteger("1044828412775859923625996009060674407404291696944").toByteArray();
        NodeId target = new NodeId(bytes);

        List<Contact> contacts = node.getContacts();

        for (Contact c : contacts) {
            node.find_node(c, target);
        }
    }
}