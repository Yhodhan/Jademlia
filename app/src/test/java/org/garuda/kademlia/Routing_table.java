package org.garuda.kademlia;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class RoutingTableTest {
    // Test bucket index
    @Test
    void bucketIndexZeroWhenIdsIdentical() {
        byte[] bytes1 = new byte[20]; // all zeros
        bytes1[0] = 1;

        NodeId self = new NodeId(bytes1);

        RoutingTable table = new RoutingTable(self);

        assertEquals(table.bucketIndex(self), 0);
    }

    @Test
    void bucketIndexLastBitDifferent() {
        byte[] bytes1 = new byte[20]; // all zeros
        byte[] bytes2 = new byte[20]; // all zeros
        bytes2[19] = 0b00000001;

        NodeId self = new NodeId(bytes1);
        NodeId a = new NodeId(bytes2);

        RoutingTable table = new RoutingTable(self);

        assertEquals(table.bucketIndex(a), 159);
    }
}
