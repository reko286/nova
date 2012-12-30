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

/**
 * Created by Hadyn Richard
 * 
 * Describes a packet.
 */
public final class PacketDescriptor {
    
    /**
     * The opcode for the packet.
     */
    private final int opcode;
    
    /**
     * The size for the packet.
     */
    private final PacketSize size;

    /**
     * Constructs a new {@link PacketDescriptor};
     * 
     * @param opcode    The packet opcode.
     */
    public PacketDescriptor(int opcode, PacketSize size) {
        this.opcode = opcode;
        this.size = size;
    }
    
    /**
     * Gets the opcode for this descriptor.
     * 
     * @return  The opcode.
     */
    public int getOpcode() {
        return opcode;
    }
    
    /**
     * Gets the packet size for this descriptor.
     * 
     * @return  The size.
     */
    public PacketSize getSize() {
        return size;
    }
}
