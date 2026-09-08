package org.garuda.kademlia.rpc;

import java.net.InetAddress;

public record PendingTx(InetAddress address, MessageType type) {

}
