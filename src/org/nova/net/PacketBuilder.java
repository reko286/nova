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
 * Used to help build and create packets.
 */
public final class PacketBuilder {
    
    /**
     * The opcode for the packet.
     */
    private int opcode;
    
    /**
     * The size for the packet.
     */
    private PacketSize size;
    
    /**
     * Constructs a new {@link PacketBuilder};
     * 
     * @param opcode    The packet opcode.
     */
    public PacketBuilder(int opcode, PacketSize size) {
        this.opcode = opcode;
        this.size = size;
    }
    
    /**
     * Sets the opcode and size of the packet.
     * 
     * @param opcode    The opcode of the packet.
     * @param size      The size of the packet.
     */
    public void setOpcodeAndSize(int opcode, PacketSize size) {
        this.opcode = opcode;
        this.size = size;
    }
    
    /**
     * Converts the information provided by this builder to a packet.
     * 
     * @return  The created packet.
     */
    public Packet toPacket() {
        PacketDescriptor descriptor = new PacketDescriptor(opcode, size);
        return new Packet(descriptor);
    }
}
