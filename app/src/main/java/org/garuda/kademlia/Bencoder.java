package org.garuda.kademlia;

import java.util.Map;

import com.dampcake.bencode.Bencode;
import com.dampcake.bencode.Type;

public class Bencoder {
    public static final Bencode BENCODER = new Bencode(true);

    private Bencoder() {
    }

    public static byte[] encodeMap(Map<String, Object> value) {
        return BENCODER.encode(value);
    }

    public static Object decodeMap(byte[] value) {
        return BENCODER.decode(value, Type.DICTIONARY);
    }

}
