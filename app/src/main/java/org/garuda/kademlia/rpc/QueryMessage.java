package org.garuda.kademlia.rpc;

import java.util.LinkedHashMap;
import java.util.Map;

import org.garuda.kademlia.Bencoder;
import org.garuda.kademlia.NodeId;

public class QueryMessage {

    public static byte[] ping(byte[] tid, NodeId id) {
        Map<String, Object> msg = new LinkedHashMap<>();
        msg.put("t", tid);
        msg.put("y", "q");
        msg.put("q", "ping");

        Map<String, Object> args = new LinkedHashMap<>();
        args.put("id", id.id());
        msg.put("a", args);

        return Bencoder.encode(msg);
    }

    public static byte[] findNode(byte[] tid, NodeId id, NodeId target) {
        Map<String, Object> msg = new LinkedHashMap<>();
        msg.put("t", tid);
        msg.put("y", "q");
        msg.put("q", "find_node");

        Map<String, Object> args = new LinkedHashMap<>();
        args.put("id", id.id());
        args.put("target", target.id());
        msg.put("a", args);

        return Bencoder.encode(msg);
    }
}