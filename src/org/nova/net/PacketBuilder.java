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
import java.nio.ByteOrder;

/**
 * Created by Hadyn Richard
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
     * Constructs a new {@link PacketBuilder};
     * The default for the packet size is STATIC.
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
     * @param descriptor    The packet descriptor.
     */
    public PacketBuilder(PacketDescriptor descriptor) {
        this.descriptor = descriptor;
    }

    /**
     * Sets the order of the buffer.
     *
     * @param order The byte order.
     * @return      This instance of the packet builder, for chaining.                     
     */
    public PacketBuilder setOrder(ByteOrder order) {
        payload.order(order);
        return this;
    }

    /**
     * Puts a byte into the payload of the packet. Resets the byte order to BIG_ENDIAN as well.
     *
     * @param b The byte value to put into the payload.
     * @return  This instance of the packet builder, for chaining.
     */
    public PacketBuilder put(byte b) {
        payload.put(b);
        payload.order(ByteOrder.BIG_ENDIAN);
        return this;
    }

    /**
     * Puts a short into the payload of the packet. Resets the byte order to BIG_ENDIAN as well.
     *
     * @param s The short value to put into the payload.
     * @return  This instance of the packet builder, for chaining.
     */
    public PacketBuilder putShort(short s) {
        payload.putShort(s);
        payload.order(ByteOrder.BIG_ENDIAN);
        return this;
    }

    /**
     * Puts a byte into the payload of the packet. Resets the byte order to BIG_ENDIAN as well.
     *
     * @param i The integer value to put into the payload.
     * @return  This instance of the packet builder, for chaining.
     */
    public PacketBuilder putInt(int i) {
        payload.putInt(i);
        payload.order(ByteOrder.BIG_ENDIAN);
        return this;
    }

    /**
     * Puts a long into the payload of the packet. Resets the byte order to BIG_ENDIAN as well.
     *
     * @param l The long value to put into the payload.
     * @return  This instance of the packet builder, for chaining.
     */
    public PacketBuilder putLong(long l) {
        payload.putLong(l);
        payload.order(ByteOrder.BIG_ENDIAN);
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
