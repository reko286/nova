/**
 * Copyright (c) 2012, Hadyn Richard
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy 
 * of this software and associated documentation files (the "Software"), to deal 
 * in the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in 
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL 
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN 
 * THE SOFTWARE.
 */

package org.nova.net;

import org.nova.net.packet.Packet;
import org.nova.net.packet.codec.PacketDecoder;
import org.nova.net.packet.codec.PacketDecoderState;
import org.nova.net.packet.codec.PacketEncoder;
import org.nova.net.packet.codec.PacketEncoderState;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hadyn Richard
 */
public final class PacketHandler {

    /**
     * The map that contains all the packet encoders.
     */
    private Map<String, PacketEncoder> encoders;

    /**
     * The map that contains all the packet decoders.
     */
    private Map<Integer, PacketDecoder> decoders;

    /**
     * Constructs a new {@link PacketHandler};
     */
    public PacketHandler() {
        encoders = new HashMap<String, PacketEncoder>();
        decoders = new HashMap<Integer, PacketDecoder>();
    }

    /**
     * Register a packet encoder.
     *
     * @param encoder       The packet encoder to register.
     */
    public void register(PacketEncoder encoder) {
        encoders.put(encoder.getPacketName(), encoder);
    }

    /**
     * Encodes a packet encoder state into a buffer.
     *
     * @param state The packet encoder state to encode.
     * @return      The encoded buffer.
     */
    public ByteBuffer encode(PacketEncoderState state) {

        /* Check if the encoder map contains a value for the specified packet */
        String packetName = state.getPacket().getName();
        if(!encoders.containsKey(packetName)) {
            return null;
        }

        /* Encode the packet encoder state into a buffer */
        PacketEncoder encoder = encoders.get(packetName);
        return encoder.encode(state);
    }

    /**
     * Register a packet encoder.
     *
     * @param decoder       The packet decoder to register.
     */
    public void register(PacketDecoder decoder) {
        decoders.put(decoder.getId(), decoder);
    }

    /**
     * Decodes a packet decoder state into a packet.
     *
     * @param state The packet encoder state to encode.
     * @return      The encoded buffer.
     */
    public Packet decode(PacketDecoderState state) {

        /* Check if the decoder map contains a value for the specified decoder */
        int decoderId = state.getDecoderId();
        if(!decoders.containsKey(decoderId)) {
            return null;
        }

        /* Encode the packet decoder state into a packet */
        PacketDecoder encoder = decoders.get(decoderId);
        return encoder.decode(state);
    }
}
