package org.garuda.kademlia;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    public Contact getContact(NodeId id) {
        NodeId distance = new NodeId(id.xorDistance(this.selfId));
        int index = bucketIndex(distance);
        KBucket bucket = getBucket(index);
        return bucket.getContact(id);
    }

    public List<Contact> getContacts() {
        return Arrays.stream(this.buckets).filter(Objects::nonNull)
                .flatMap(bucket -> bucket.getContacts().stream())
                .collect(Collectors.toList());
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

    private KBucket getBucket(int index) {
        return buckets[index];
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