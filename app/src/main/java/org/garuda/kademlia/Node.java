package org.garuda.kademlia;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;

public class Node {

    private final NodeId id;
    private RoutingTable routingTable;
    private HashMap<NodeId, Object> tokens;
    private DatagramSocket socket;

    public Node() throws Exception {
        this.id = NodeId.generateRandom();
        this.routingTable = new RoutingTable(this.id);
        this.tokens = new HashMap<>();

        // open standart torrent port
        this.socket = new DatagramSocket(6881);
    }

    // Init the listening loop
    public void init() {

    }

    public void ping(InetAddress address, DatagramSocket port) {
    }
}