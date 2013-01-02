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

package org.nova.net.codec;

import org.nova.net.Packet;
import org.nova.net.PacketBlock;
import org.nova.net.PacketSize;
import org.nova.net.packet.Transformer;
import org.nova.util.Encoder;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Hadyn Richard
 *
 * Notes:
 *
 *          Blocks are encoded in order by which they were entered into the encoder.
 *          TODO: Differentiate stateful and stateless packet encoder.
 */
public final class PacketEncoder implements Encoder<Packet, ByteBuffer> {

    /**
     * The map of transformers to use with specified blocks.
     */
    private Map<String, Transformer> transformers;

    /**
     * The blocks to use to encode the packet with.
     */
    private List<String> blocks;

    /**
     * Construct a new {@link PacketEncoder};
     */
    public PacketEncoder() {
        transformers = new HashMap<String, Transformer>();
        blocks = new ArrayList<String>();
    }

    /**
     * Adds a block of a packet to encode.
     *
     * @param name  The name of the block to add.
     */
    public void addBlock(String name) {
        blocks.add(name);
    }

    /**
     * Adds a transformer for a specific block.
     *
     * @param name          The name of the block.
     * @param transformer   The transformer to add.
     */
    public void addTransformer(String name, Transformer transformer) {
        transformers.put(name, transformer);
    }

    @Override
    public ByteBuffer encode(Packet packet) {

        /* Check if the packet contains the required blocks */
        if(containsRequiredBlocks(packet)) {
            throw new IllegalStateException("Packet does not contain required blocks");    // Possibly return null?
        }
        
        /* Calculate the length of the body */
        int length = 0;
        for(String name : blocks) {
            PacketBlock block = packet.getPacketBlock(name);
            length += block.getLength();
        }
        
        /* Calculate the length of the header */
        int headerLength = 1;                                                       // Include the opcode
        headerLength += getLengthForPacketSize(packet.getDescriptor().getSize());

        /* Allocate the byte buffer */
        ByteBuffer buffer = ByteBuffer.allocate(headerLength + length);

        /* Put the opcode of the packet TODO: Add in the cryption */
        buffer.put((byte) packet.getDescriptor().getOpcode());

        /* Encode the size of the packet into the buffer */
        PacketSize size = packet.getDescriptor().getSize();
        if(size != PacketSize.STATIC) {
            if(size == PacketSize.VAR_SHORT) {
                buffer.putShort((short) length);
            } else {
                buffer.put((byte) length);
            }
        }

        /* Encode each of the blocks into the buffer */
        for(String name : blocks) {
            PacketBlock block = packet.getPacketBlock(name);

            /* Check if there is a transformer for a block and transform the block if needed */
            if(transformers.containsKey(name)) {
                block.encodeValue(transformers.get(name));
            }

            block.encode(buffer);
        }

        return buffer;
    }

    /**
     * Checks if a packet contains all the required blocks.
     *
     * @param packet    The packet to check if it contains the required blocks.
     * @return          If the packet contains the required blocks.
     */
    private boolean containsRequiredBlocks(Packet packet) {
        for(String name : blocks) {
            if(!packet.containsBlock(name)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Gets the length of a packet size.
     *
     * @param size  The packet size to get the length for.
     * @return      The length for the provided packet size.
     */
    private static int getLengthForPacketSize(PacketSize size) {
        switch(size) {
            case STATIC:
                return 0;

            case VAR_BYTE:
                return 1;

            case VAR_SHORT:
                return 2;

            default:
                throw new RuntimeException("Unhandled packet size");
        }
    }
}
