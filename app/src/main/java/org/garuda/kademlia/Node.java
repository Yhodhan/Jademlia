package org.garuda.kademlia;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import org.garuda.kademlia.rpc.Message;
import org.garuda.kademlia.rpc.MessageType;
import org.garuda.kademlia.rpc.PendingTx;

public class Node {

    public static final SecureRandom RANDOM = new SecureRandom();
    private static final Logger logger = Logger.getLogger(Node.class.getName());

    private volatile Boolean running = false;
    private int port = 6881;
    private final NodeId id;
    private RoutingTable routingTable;
    private Map<NodeId, Object> tokens;
    private DatagramSocket socket;
    private Thread listener;
    private final Map<String, PendingTx> pending;

    public Node() throws Exception {
        this.id = NodeId.generateRandom();
        this.routingTable = new RoutingTable(this.id);
        this.tokens = new ConcurrentHashMap<>();
        this.pending = new ConcurrentHashMap<>();

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

        // store transaction
        String tidKey = Message.hex(tid);
        pending.put(tidKey, new PendingTx(address, MessageType.PING));

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
                handlePacket(received, packet.getAddress(), packet.getPort());
            } catch (IOException e) {
                if (running) {
                    System.err.println("Error receiving packet: " + e.getMessage());
                }
            }
        }
    }

    // -------------------------
    // Package reception
    // -------------------------
    private void handlePacket(byte[] packet, InetAddress fromAddress, int port) {
        Map<String, Object> message;
        try {
            message = (Map<String, Object>) Bencoder.decodeMap(packet);
        } catch (Exception e) {
            System.err.println("Malformed package from " + fromAddress + ":" + port);
            return;
        }

        String y = Message.getString(message, "y");

        if ("q".equals(y)) { // Query
            handleQuery(message);
        } else if ("r".equals(y)) { // Response
            handleResponse(message, fromAddress, port);
        } else if ("e".equals(y)) { // Error
            handleError(message);
        }
    }

    private void handleQuery(Map<String, Object> message) {
        // TODO:
    }

    private void handleResponse(Map<String, Object> message, InetAddress address, int port) {
        byte[] tid = Message.getBytes(message, "t");
        String tidKey = Message.hex(tid);

        PendingTx tx = pending.remove(tidKey);
        if (tx == null) {
            System.err.println("Unknown of expired request" + tidKey);
            return;
        }
        // Handle the response
        switch (tx.type()) {
            case MessageType.PING:
                handlePing(message, address, port);
                break;

            default:
                System.err.println("Unknown Message type");
                break;
        }
    }

    private void handleError(Map<String, Object> message) {
        // TODO:
    }

    // -------------------------
    // Handle each API call
    // -------------------------
    private void handlePing(Map<String, Object> message, InetAddress address, int port) {
        // get contact
        Map<String, Object> contactInfo = (Map<String, Object>) message.get("r");
        byte[] contactId = Message.getBytes(contactInfo, "id");

        System.out.println("Contact id: " + contactId);
        NodeId nodeId = new NodeId(contactId);
        Contact contact = new Contact(nodeId, address, port);
        // store contact
        this.routingTable.insert(contact);

        logger.info("insertion succesfull");
    }
}