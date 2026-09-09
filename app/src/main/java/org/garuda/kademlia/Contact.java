package org.garuda.kademlia;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public record Contact(NodeId id, InetAddress address, int port) {
    public static List<Contact> decodeContacts(byte[] nodes) {
        List<Contact> contacts = new ArrayList<>();
        for (int i = 0; i < nodes.length; i += 26) {
            byte[] id = Arrays.copyOfRange(nodes, i, i + 20);
            byte[] ipAddress = Arrays.copyOfRange(nodes, i + 20, i + 24);
            int port = ((nodes[i + 24] & 0xFF) << 8) | (nodes[i + 25] & 0xFF);

            try {
                NodeId nodeId = new NodeId(id);
                InetAddress address = InetAddress.getByAddress(ipAddress);
                Contact contact = new Contact(nodeId, address, port);
                contacts.add(contact);
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            }
        }

        return contacts;
    }

    public static byte[] encodeContacts(List<Contact> contacts) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        for (Contact c : contacts) {
            try {
                out.write(c.id().id());
                out.write(c.address().getAddress());
                int port = c.port();
                out.write((port >> 8) & 0xFF);
                out.write(port & 0xFF);

            } catch (IOException e) {
                throw new RuntimeException("Failed to encode contacts", e);
            }
        }
        return out.toByteArray();
    }
}