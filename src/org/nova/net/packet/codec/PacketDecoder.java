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

package org.nova.net.packet.codec;

import org.nova.core.ServiceType;
import org.nova.util.meta.PacketData;
import org.nova.net.packet.Packet;
import org.nova.net.packet.PacketBlock;
import org.nova.util.Decoder;

import java.nio.ByteBuffer;

/**
 * Created by Hadyn Richard
 *
 * Notes:
 *
 *          - Possible check if factory creates packets that have the correct blocks in the constructor?
 */
public final class PacketDecoder extends PacketCodec implements Decoder<Packet, PacketDecoderState> {

    /**
     * The id for this decoder.
     */
    private int id;

    /**
     * The meta data to use to create new packets.
     */
    private PacketData data;

    /**
     * Constructs a new {@link PacketDecoder};
     *
     * @param id            The id for this decoder.
     * @parma data      The meta data to use to create new packets.
     */
    public PacketDecoder(int id, PacketData data) {
        this.id = id;
        this.data = data;
    }

    /**
     * Gets the id of this decoder.
     *
     * @return  The id.
     */
    public int getId() {
        return id;
    }

    /**
     * Decodes the packet.
     *
     * @param state The state to use to decode the packet.
     * @return      Upon returning null the bytes to successfully read the packet have not yet be fully received.
     */
    @Override
    public Packet decode(PacketDecoderState state) {

        /* Check if we are waiting for all the bytes to be sent to read the packet */
        if(!state.getStage().equals(PacketDecoderState.Stage.AWAITING_BYTES)) {
            throw new IllegalStateException("invalid stage");
        }

        /* Get and mark the buffer */
        ByteBuffer buffer = state.getBuffer();
        buffer.mark();

        /* Parse the size if needed */
        int size = data.getSize();
        if(data.isVarietySized()) {
            
            if(data.isVarietyByteSized()) {

                /* Check if we have the required amount of bytes */
                if(buffer.remaining() < 1) {
                    return null;
                }

                size = buffer.get() & 0xFF;
            }  else {

                /* Check if we have the required amount of bytes */
                if(buffer.remaining() < 2) {
                    return null;
                }

                size = buffer.getShort() & 0xFFFF;
            }
        }


        /* Check if we have the adequate amount of bytes to read for the packet */
        if(buffer.remaining() < size) {

            /* Reset the buffer and return null */
            buffer.reset();
            return null;
        }

        /* Create a new packet */
        Packet packet = data.create();

        /* Check if the packet contains the required blocks */
        if(!containsRequiredBlocks(packet)) {
            throw new IllegalStateException("packet does not contain required blocks");
        }

        /* Encode each of the blocks into the buffer */
        for(String name : blocks) {
            PacketBlock block = packet.getBlock(name);

            /* Decode the block */
            block.decode(state.getBuffer());

            /* Check if there is a transformer for a block and transform the block if needed */
            if(transformers.containsKey(name)) {
                block.decodeValue(transformers.get(name));
            }
        }

        return packet;
    }
}
