package org.garuda.kademlia;

import java.util.LinkedList;

public class KBucket {
    private static final int K = 8;
    private final LinkedList<Contact> contacts = new LinkedList<>();

    public synchronized boolean add(Contact contact) {
        contacts.remove(contact);
        if (contacts.size() < K) {
            contacts.addLast(contact);
            return true;
        }
        return false;
    }

    public Contact getContact(NodeId id) {
        for (Contact c : contacts) {
            if (c.id().equals(id)) {
                return c;
            }
        }
        return null;
    }

    public synchronized Contact lastRecentlySeen() {
        return contacts.peekFirst();
    }

    public synchronized LinkedList<Contact> getContacts() {
        return contacts;
    }
}
