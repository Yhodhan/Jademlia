package org.garuda.kademlia.rpc;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.garuda.kademlia.Bencoder;
import org.garuda.kademlia.Contact;
import org.garuda.kademlia.NodeId;

public class ResponseMessage {
    public static byte[] pong(byte[] tid, NodeId id) {
        Map<String, Object> msg = new LinkedHashMap<>();
        msg.put("t", tid);
        msg.put("y", "r");

        Map<String, Object> r = new LinkedHashMap<>();
        r.put("id", id.id());
        msg.put("r", r);

        return Bencoder.encode(msg);
    }

    public static byte[] findNode(byte[] tid, NodeId id, List<Contact> contacts) {
        Map<String, Object> msg = new LinkedHashMap<>();
        msg.put("t", tid);
        msg.put("y", "r");

        Map<String, Object> r = new LinkedHashMap<>();
        r.put("id", id.id());
        r.put("nodes", Contact.encodeContacts(contacts)); // helper, similar to FindNodeReplyPayload from earlier
        msg.put("r", r);

        return Bencoder.encode(msg);
    }
}
