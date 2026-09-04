package org.garuda.kademlia.rpc;

import java.util.UUID;

import org.garuda.kademlia.Contact;

public record Message(
        MessageType type,
        UUID rpcId,
        Contact sender,
        byte[] payload) {
}
