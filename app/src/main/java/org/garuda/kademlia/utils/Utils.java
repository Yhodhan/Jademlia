package org.garuda.kademlia.utils;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.HexFormat;
import java.util.Map;

import org.garuda.kademlia.Node;

public class Utils {
    private Utils() {
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

    public static String bufferToString(ByteBuffer buffer) {
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
