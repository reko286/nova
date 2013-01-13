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
     * The numeric representation for a variety byte sized packet.
     */
    public static final int VAR_BYTE = -1;

    /**
     * The numeric representation for a variety short sized packet.
     */
    public static final int VAR_SHORT = -2;

    /**
     * The opcode for the packet.
     */
    private int opcode;

    /**
     * The size of the packet.
     */
    private int size;

    /**
     * Constructs a new {@link PacketDescriptor};
     * 
     * @param opcode    The packet opcode.
     * @param size      The size of the packet.
     */
    public PacketDescriptor(int opcode, int size) {
        this.opcode = opcode;
        this.size = size;

        /* Check if the size is valid */
        if(size != VAR_BYTE && size != VAR_SHORT && size < 0) {
            throw new IllegalArgumentException("invalid size");
        }
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
     * Gets if this packet is variety short or byte sized.
     *
     * @return  If the packet is variety sized.
     */
    public boolean isVarietySized() {
        return size == VAR_BYTE || size == VAR_SHORT;
    }

    /**
     * Gets if this packet is variety byte sized.
     *
     * @return  If the packet is variety byte sized.
     */
    public boolean isVarietyByteSized() {
        return size == VAR_BYTE;
    }

    /**
     * Gets if this packet is variety short sized.
     *
     * @return  If the packet is variety short sized.
     */
    public boolean isVarietyShortSized() {
        return size == VAR_SHORT;
    }
    
    /**
     * Gets the packet size for this descriptor.
     * 
     * @return  The size.
     */
    public int getSize() {
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null || !(obj instanceof PacketDescriptor)) {
            return false;
        }

        PacketDescriptor other = (PacketDescriptor) obj;
        return other.size == size && other.opcode == opcode;
    }
}
