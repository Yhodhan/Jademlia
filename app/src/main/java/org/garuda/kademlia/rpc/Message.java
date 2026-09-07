package org.garuda.kademlia.rpc;

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

    public static byte[] generateTID() {
        byte[] id = new byte[2];
        Node.RANDOM.nextBytes(id);
        return id;
    }

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
}
