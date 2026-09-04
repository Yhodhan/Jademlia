package org.garuda.kademlia;

import java.net.InetAddress;

public record Contact(NodeId id, InetAddress address, int port) {

}