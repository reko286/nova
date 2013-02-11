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

import org.nova.net.packet.Transformer;
import org.nova.net.packet.codec.PacketCodec;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Hadyn Richard
 */
public abstract class PacketCodecData<T extends PacketCodec> {

    /**
     * The id associated with the decoder.
     */
    protected int id;

    /**
     * The name of the packet associated with the decoder.
     */
    protected String packetName;

    /**
     * The blocks for the decoder.
     */
    protected List<Block> blocks;

    /**
     * Constructs a new {@link PacketDecoderData};
     *
     * @param id            The id associated with the decoder.
     * @param packetName    The name of the packet associated with the decoder.
     */
    protected PacketCodecData(int id, String packetName) {
        this.id = id;
        this.packetName = packetName;

        blocks = new LinkedList<Block>();
    }

    /**
     * The inline class for each of the blocks for the decoder.
     */
    protected class Block {

        /**
         * The name of the block.
         */
        protected String name;

        /*
         * The transformer associated with the block.
         */
        protected Transformer transformer;

        /**
         * The flag representing if the transformer should be used. I hate using null state.
         */
        protected boolean useTransformer;

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

}