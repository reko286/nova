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

import java.util.Map;

/**
 * Created by Hadyn Richard
 */
public final class Packet {    
        
    /**
     * The descriptor for this packet.
     */
    private final PacketDescriptor descriptor;

    /**
     * The packet blocks for this packet.
     */
    private Map<String, PacketBlock> packetBlocks;
    
    /**
     * Constructs a new {@link Packet};
     * 
     * @param descriptor    The descriptor for the packet.
     * @param packetBlocks  The packet blocks for the packet.
     */
    public Packet(PacketDescriptor descriptor, Map<String, PacketBlock> packetBlocks) {
        this.descriptor = descriptor;
        this.packetBlocks = packetBlocks;
    }
    
    /**
     * Gets the descriptor for this packet.
     * 
     * @return  The descriptor.
     */
    public PacketDescriptor getDescriptor() {
        return descriptor;
    }

    /**
     * Gets the blocks for this packet.
     *
     * @return  The packet blocks.
     */
    public Map<String, PacketBlock> getPacketBlocks() {
    	return packetBlocks;
    }
}