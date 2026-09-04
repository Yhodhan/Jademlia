package org.garuda.kademlia;

public class RoutingTable {

    private final NodeId selfId;
    private final KBucket[] buckets = new KBucket[160];

    public RoutingTable(NodeId selfId) {
        this.selfId = selfId;
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new KBucket();
        }
    }

    public void insert(Contact contact) {
        int index = bucketIndex(contact.id());
        buckets[index].add(contact);
    }

    protected int bucketIndex(NodeId id) {
        byte[] distance = id.xorDistance(this.selfId);
        // check the fist non 0 bit
        for (int i = 0; i < distance.length; i++) {
            int b = distance[i] & 0xFF;
            if (b != 0) {
                int leadingZeros = Integer.numberOfLeadingZeros(b) - 24;
                return (i * 8) + leadingZeros;
            }
        }
        // route to itself
        return 0;
    }

    public static int compareDistances(byte[] d1, byte[] d2) {
        for (int i = 0; i < d1.length; i++) {
            int a = d1[i] & 0xff;
            int b = d2[i] & 0xff;
            if (a != b)
                return Integer.compare(a, b);
        }

        return 0;
    }
}