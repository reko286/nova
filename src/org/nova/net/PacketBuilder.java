/**
 * Copyright Evelus, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Hadyn Richard (sini@evel.us), July 2012
 */

package org.nova.net;

import java.nio.ByteBuffer;

/**
 * Evelus Development
 * Created by Hadyn Richard
 * 
 * Used to help build and create packets.
 */
public final class PacketBuilder {
    
    private PacketDescriptor descriptor;
    private ByteBuffer payload;
    
    public PacketBuilder(int opcode) {
    	this(opcode, PacketSize.STATIC);
    }

    public PacketBuilder(int opcode, PacketSize size) {
    	this(new PacketDescriptor(opcode, size));
    }
    
    /**
     * Constructs a new {@link PacketBuilder};
     * 
     * @param opcode    The packet opcode.
     */
    public PacketBuilder(PacketDescriptor descriptor) {
    	this.descriptor = descriptor;
    }
    
	public PacketBuilder put(byte b) {
		payload.put(b);
		return this;
	}
	
	public PacketBuilder putChar(char c) {
		payload.putChar(c);
		return this;
	}
	
	public PacketBuilder putShort(short s) {
		payload.putShort(s);
		return this;
	}
	
	public PacketBuilder putInt(int i) {
		payload.putInt(i);
		return this;
	}
	
	public PacketBuilder putFloat(float f) {
		payload.putFloat(f);
		return this;
	}
	
	public PacketBuilder putDouble(double d) {
		payload.putDouble(d);
		return this;
	}
	
	public PacketBuilder putLong(long l) {
		payload.putLong(l);
		return this;
	}
    
    /**
     * Converts the information provided by this builder to a packet.
     * 
     * @return  The created packet.
     */
    public Packet toPacket() {
        return new Packet(descriptor, payload);
    }
}
