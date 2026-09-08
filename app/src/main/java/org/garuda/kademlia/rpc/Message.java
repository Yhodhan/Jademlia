package org.garuda.kademlia.rpc;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.HexFormat;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import org.garuda.kademlia.Bencoder;
import org.garuda.kademlia.Contact;
import org.garuda.kademlia.Node;
import org.garuda.kademlia.NodeId;

public record Message(
        MessageType type,
        UUID rpcId,
        Contact sender,
        byte[] payload) {

    // ---------------------------------
    // Message creation
    // ---------------------------------

    public static byte[] createPingMessage(byte[] tid, NodeId id) {
        Map<String, Object> query = new LinkedHashMap<>();
        query.put("t", tid);
        query.put("y", "q");
        query.put("q", "ping");

        Map<String, Object> args = new LinkedHashMap<>();
        args.put("id", id.id());
        query.put("a", args);

        return Bencoder.encodeMap(query);
    }

    // ---------------------------------
    // Helper functions
    // ---------------------------------
    public static byte[] generateTID() {
        byte[] id = new byte[2];
        Node.RANDOM.nextBytes(id);
        return id;
    }

    public static String hex(byte[] bytes) {
        return HexFormat.of().formatHex(bytes);
    }

    public static byte[] getBytes(Map<String, Object> message, String key) {
        ByteBuffer buffer = (ByteBuffer) message.get(key);
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);
        return bytes;
    }

    public static String getString(Map<String, Object> message, String key) {
        return new String(getBytes(message, key), StandardCharsets.UTF_8);
    }
}
