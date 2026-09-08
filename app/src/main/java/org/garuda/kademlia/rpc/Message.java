package org.garuda.kademlia.rpc;

import java.util.LinkedHashMap;
import java.util.Map;

import org.garuda.kademlia.Bencoder;
import org.garuda.kademlia.NodeId;

public class Message {

    // ---------------------------------
    // Message creation
    // ---------------------------------

    public static byte[] createPingMessage(byte[] tid, NodeId id, String y, MessageType type) {
        Map<String, Object> msg = new LinkedHashMap<>();
        msg.put("t", tid);
        msg.put("y", y);

        Map<String, Object> args = new LinkedHashMap<>();
        args.put("id", id.id());

        switch (type) {
            case PING:
                msg.put("q", "ping");
                msg.put("a", args);
                break;
            default: // Default PONG
                msg.put("r", args);
                break;
        }

        return Bencoder.encodeMap(msg);
    }

}
