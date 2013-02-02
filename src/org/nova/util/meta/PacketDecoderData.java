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

package org.nova.util.meta;

import org.nova.core.ServiceType;
import org.nova.net.packet.Transformer;
import org.nova.net.packet.codec.impl.PacketDecoder;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Hadyn Richard
 */
public final class PacketDecoderData {

    /**
     * The service type associated with the decoder.
     */
    private ServiceType serviceType;

    /**
     * The id associated with the decoder.
     */
    private int id;

    /**
     * The packet associated with the decoder.
     */
    private String packet;

    /**
     * The blocks for the decoder.
     */
    private List<Block> blocks;

    /**
     * Constructs a new {@link PacketDecoderData};
     * 
     * @param serviceType   The service type associated with the decoder.
     * @param id            The id associated with the decoder.
     * @param packet        The name of the packet associated with the decoder.
     */
    public PacketDecoderData(ServiceType serviceType, int id, String packet) {
        this.serviceType = serviceType;
        this.id = id;
        this.packet = packet;
        
        blocks = new LinkedList<Block>();
    }

    /**
     * The inline class for each of the blocks for the decoder.
     */
    private class Block {

        /**
         * The name of the block.
         */
        private String name;

        /*
         * The transformer associated with the block.
         */
        private Transformer transformer;

        /**
         * The flag representing if the transformer should be used. I hate using null state.
         */
        private boolean useTransformer;

    }

    /**
     * Add a block to the block list.
     *
     * @param name          The name of the block to add.
     */
    public void addBlock(String name) {

        /* Check if the name is null */
        if(name == null) {
            throw new IllegalArgumentException("name cannot be null");
        }

        /* Create the new block and set all the fields */
        Block block = new Block();
        block.useTransformer = false;

        blocks.add(block);
    }

    /**
     * Add a block to the block list.
     *
     * @param name          The name of the block to add.
     * @param transformer   The transformer for the block.
     */
    public void addBlock(String name, Transformer transformer) {

        /* Check if the name is null */
        if(name == null) {
            throw new IllegalArgumentException("name cannot be null");
        }

        /* Check if the transformer is null */
        if(transformer == null) {
            throw new IllegalArgumentException("transformer cannot be null");
        }
        
        /* Create the new block and set all the fields */
        Block block = new Block();
        block.name = name;
        block.transformer = transformer;
        block.useTransformer = true;

        blocks.add(block);
    }

    /**
     * Gets the name of the packet to decorate for.
     *
     * @return  The packet.
     */
    public String getPacket() {
        return packet;
    }

    /**
     * Create a new packet decoder from the meta data.
     *
     * @param packetData    The packet meta data to use to create the packet decoder.
     * @return                  The created packet decoder.
     */
    public PacketDecoder create(PacketData packetData) {

        /* Create the packet decoder */
        PacketDecoder decoder = new PacketDecoder(id, serviceType, packetData);

        for(Block block : blocks) {

            /* Add the block to the decoder */
            decoder.addBlock(block.name);

            /* Check if a transformer needs to be used */
            if(block.useTransformer) {

                /* Add the transformer to the decoder */
                decoder.addTransformer(block.name, block.transformer);
            }
        }

        return decoder;
    }
}
