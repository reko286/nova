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
 */
public final class Packet {    
        
    /**
     * The descriptor for this packet.
     */
    private final PacketDescriptor descriptor;
    
    /**
     * Constructs a new {@link Packet};
     * 
     * @param descriptor    The descriptor for the packet.
     */
    public Packet(PacketDescriptor descriptor) {
        this.descriptor = descriptor;
    }
    
    /**
     * Gets the descriptor for this packet.
     * 
     * @return  The descriptor.
     */
    public PacketDescriptor getDescriptor() {
        return descriptor;
    }
}