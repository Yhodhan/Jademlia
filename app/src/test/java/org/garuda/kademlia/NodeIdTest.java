package org.garuda.kademlia;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class NodeIdTest {
    @Test
    void equalIdsWithSameBytesAreEqual() {
        NodeId a = new NodeId(new byte[20]); // all zeros
        NodeId b = new NodeId(new byte[20]); // different array same content

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void differentBytesAreNotEqual() {
        byte[] bytes1 = new byte[20]; // all zeros
        byte[] bytes2 = new byte[20]; // different array same content
        bytes2[0] = 1;

        NodeId a = new NodeId(bytes1);
        NodeId b = new NodeId(bytes2);

        assertNotEquals(b, a);
    }

    @Test
    void xorDistanceToSelfIsZero() {
        byte[] bytes = new byte[20];
        bytes[0] = 42;
        NodeId a = new NodeId(bytes);
        NodeId b = new NodeId(bytes.clone()); // same content different object

        byte[] distance = a.xorDistance(b);
        for (byte b1 : distance) {
            assertEquals(b1, 0);
        }
    }

    @Test
    void constructorRejectsWrongLength() {
        assertThrows(IllegalArgumentException.class, () -> {
            new NodeId(new byte[10]);
        });
    }
}