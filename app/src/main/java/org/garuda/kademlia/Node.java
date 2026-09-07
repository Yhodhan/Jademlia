package org.garuda.kademlia;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Logger;

import org.garuda.kademlia.rpc.Message;

public class Node {

    public static final SecureRandom RANDOM = new SecureRandom();
    private static final Logger logger = Logger.getLogger(Node.class.getName());

    private volatile Boolean running = false;
    private int port = 6881;
    private final NodeId id;
    private RoutingTable routingTable;
    private HashMap<NodeId, Object> tokens;
    private DatagramSocket socket;
    private Thread listener;

    public Node() throws Exception {
        this.id = NodeId.generateRandom();
        this.routingTable = new RoutingTable(this.id);
        this.tokens = new HashMap<>();

        // open standart torrent port
        this.socket = new DatagramSocket(port);
    }

    // Init the listening loop for incoming calls
    public void init() {
        logger.info("=== Node listener init ====");
        running = true;
        listener = new Thread(this::listen);
        listener.start();
    }

    // ------------------------------
    // Main Api Functions
    // ------------------------------

    public void ping(InetAddress address, int port) throws Exception {
        // build message
        byte[] tid = Message.generateTID();
        byte[] message = Message.createPingMessage(tid, this.id);

        // send message
        socket.send(buildPacket(message, address, port));
    }

    // -------------------------
    // Getters
    // -------------------------
    public DatagramSocket getSocket() {
        return this.socket;
    }

    public void stop() {
        running = false;
        socket.close();
    }

    // -------------------------
    // Private functions
    // -------------------------

    private DatagramPacket buildPacket(byte[] data, InetAddress address, int port) {
        return new DatagramPacket(data, data.length, address, port);
    }

    private void listen() {
        byte[] buffer = new byte[4086];
        while (running) {
            try {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                byte[] received = Arrays.copyOf(packet.getData(), packet.getLength());
                handlePacket(received);
            } catch (IOException e) {
                if (running) {
                    System.err.println("Error receiving packet: " + e.getMessage());
                }
            }
        }
    }

    private void handlePacket(byte[] packet) {

    }
}