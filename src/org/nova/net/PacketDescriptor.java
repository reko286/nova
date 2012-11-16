/**
 * Copyright Evelus, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Hadyn Richard (sini@evel.us), July 2012
 */

package org.nova.net;

/**
 * Evelus Development
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
