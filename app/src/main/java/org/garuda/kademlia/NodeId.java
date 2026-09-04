package org.garuda.kademlia;

import java.security.SecureRandom;
import java.util.Arrays;

public record NodeId(byte[] id) {

    private static final SecureRandom RANDOM = new SecureRandom();

    public NodeId {
        if (id.length != 20) {
            throw new IllegalArgumentException("NodeId must be at least 20 bytes");
        }
    }

    public static NodeId generateRandom() {
        byte[] id = new byte[20];
        RANDOM.nextBytes(id);
        return new NodeId(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof NodeId other))
            return false;

        return Arrays.equals(this.id, other.id);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(id);
    }

    @Override
    public String toString() {
        return bytesToHex(id);
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }

    public byte[] xorDistance(NodeId other) {
        byte[] result = new byte[this.id.length];
        for (int i = 0; i < this.id.length; i++) {
            result[i] = (byte) (this.id[i] ^ other.id[i]);
        }

        return result;
    }
}