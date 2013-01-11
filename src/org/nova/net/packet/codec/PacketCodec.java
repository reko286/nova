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

import org.nova.net.Packet;
import org.nova.net.packet.Transformer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Hadyn Richard
 */
public abstract class PacketCodec {

    /**
     * The map of transformers to use with specified blocks.
     */
    protected Map<String, Transformer> transformers;

    /**
     * The blocks to use to encode the packet with.
     */
    protected List<String> blocks;

    /**
     * Constructs a new {@link PacketCodec};
     *
     * Protected access to prevent inline method construction.
     */
    protected PacketCodec() {
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

    /**
     * Checks if a packet contains all the required blocks.
     *
     * @param packet    The packet to check if it contains the required blocks.
     * @return          If the packet contains the required blocks.
     */
    public boolean containsRequiredBlocks(Packet packet) {
        for(String name : blocks) {
            if(!packet.containsBlock(name)) {
                return false;
            }
        }
        return true;
    }
}
