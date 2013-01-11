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
 *
 * Notes:
 *          Could this possibly be a bit cleaner?
 */
public final class PacketDescriptor {

    /**
     * The opcode for the packet.
     */
    private int opcode;
    
    /**
     * The size for the packet.
     */
    private PacketSize size;

    /**
     * The actual size of the packet.
     */
    private int actualSize;

    /**
     * Constructs a new {@link PacketDescriptor};
     * 
     * @param opcode    The packet opcode.
     * @param size      The size of the packet.
     */
    public PacketDescriptor(int opcode, PacketSize size) {
        this.opcode = opcode;
        this.size = size;

        /* Check if the size is valid */
        if(size == PacketSize.STATIC) {
            throw new IllegalStateException("cannot be static sized without indicating the actual size");
        }
            
        /* Set the actual size to the appropriate value based on what type of size it is */
        if(size == PacketSize.VAR_BYTE) {
            actualSize = -1;
        } else {
            actualSize = -2;
        }
    }

    /**
     * Constructs a new {@link PacketDescriptor};
     * Specifically for STATIC packets ONLY, sets the packet size to STATIC.
     *
     * 
     * @param opcode        The packet opcode.
     * @param actualSize    The actual size of the packet.
     */
    public PacketDescriptor(int opcode, int actualSize) {
        this.opcode = opcode;
        this.actualSize = actualSize;

        /* Set the packet size to static since the actual size is set */
        size = PacketSize.STATIC;
    }

    /**
     * Gets the actual size of the packet.
     *
     * @return  The actual size of the packet, if the packet is a variety byte sized then this will return
     *          negative one for variety byte sized packets and negative two for variety short sized packets.
     */
    public int getActualSize() {
        return actualSize;
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
