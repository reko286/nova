/**
 * Copyright (c) 2012, Hadyn Richard
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.nova.net;

import java.nio.ByteBuffer;

/**
 * Evelus Development Created by Hadyn Richard
 *
 * Used to help build and create packets.
 */
public final class PacketBuilder {

    /**
     * The descriptor for the packet we are building.
     */
    private PacketDescriptor descriptor;
    
    /**
     * The payload buffer.
     */
    private ByteBuffer payload;

    /**
     * Constructs a new {@link PacketBuilder}; The default for the packet size
     * is STATIC.
     *
     * @param opcode The opcode of the packet.
     */
    public PacketBuilder(int opcode) {
        this(opcode, PacketSize.STATIC);
    }

    /**
     * Constructs a new {@link PacketBuilder};
     *
     * @param opcode The packet opcode.
     * @param size The packet size.
     */
    public PacketBuilder(int opcode, PacketSize size) {
        this(new PacketDescriptor(opcode, size));
    }

    /**
     * Constructs a new {@link PacketBuilder};
     *
     * @param opcode The packet opcode.
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
     * @return The created packet.
     */
    public Packet toPacket() {
        return new Packet(descriptor, payload);
    }
}
