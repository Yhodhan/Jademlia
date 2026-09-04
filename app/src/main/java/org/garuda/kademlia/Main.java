package org.garuda.kademlia;

import java.util.LinkedHashMap;
import java.util.Map;

import com.dampcake.bencode.Bencode;

public class Main {
    public static void main(String[] args) {
        NodeId node = new NodeId(new byte[20]);

        Bencode bencode = new Bencode();
        Map<String, Object> map = new LinkedHashMap<>();

        map.put("id", node.id());
        Map<String, Object> query = new LinkedHashMap<>();

        query.put("y", "q");
        query.put("q", "ping");
        query.put("a", map);

        byte[] wireBytes = bencode.encode(query);

        System.out.println("bytes: " + wireBytes.hashCode());
    }
}