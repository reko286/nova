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

import org.nova.net.packet.NumericBlock;
import org.nova.net.packet.NumericType;
import org.nova.net.packet.StringBlock;

import java.util.HashMap;
import java.util.Map;

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
     * The packet blocks for the packet we are building.
     */
    private Map<String, PacketBlock> blocks;

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
        
        blocks = new HashMap<String, PacketBlock>();
    }

    /**
     * Puts a numeric byte block into the packet.
     *
     * @param name  The name of the block to put.
     * @return      This instance of the packet builder, for chaining.
     */
    public PacketBuilder putInt8(String name) {
        NumericBlock block = new NumericBlock(NumericType.INT8);
        blocks.put(name, block);
        return this;
    }

    /**
     * Puts a numeric byte block into the packet.
     *
     * @param name  The name of the block to put.
     * @param b     The byte value to put into the blocks.
     * @return      This instance of the packet builder, for chaining.
     */
    public PacketBuilder putInt8(String name, byte b) {
        NumericBlock block = new NumericBlock(b, NumericType.INT8);
        blocks.put(name, block);
        return this;
    }

    /**
     * Puts a numeric short block into the packet.
     *
     * @param name  The name of the block to put.
     * @return      This instance of the packet builder, for chaining.
     */
    public PacketBuilder putInt16(String name) {
        NumericBlock block = new NumericBlock(NumericType.INT16);
        blocks.put(name, block);
        return this;
    }

    /**
     * Puts a numeric short block into the packet.
     *
     * @param name  The name of the block to put.
     * @param s     The short value to put into the blocks.
     * @return      This instance of the packet builder, for chaining.
     */
    public PacketBuilder putInt16(String name, short s) {
        NumericBlock block = new NumericBlock(s, NumericType.INT16);
        blocks.put(name, block);
        return this;
    }

    /**
     * Puts a numeric integer block into the packet.
     *
     * @param name  The name of the block to put.
     * @return      This instance of the packet builder, for chaining.
     */
    public PacketBuilder putInt32(String name) {
        NumericBlock block = new NumericBlock(NumericType.INT32);
        blocks.put(name, block);
        return this;
    }

    /**
     * Puts a numeric integer block into the packet.
     *
     * @param name  The name of the block to put.
     * @param i     The integer value to put into the blocks.
     * @return      This instance of the packet builder, for chaining.
     */
    public PacketBuilder putInt32(String name, int i) {
        NumericBlock block = new NumericBlock(i, NumericType.INT32);
        blocks.put(name, block);
        return this;
    }

    /**
     * Puts a numeric long block into the packet.
     *
     * @param name  The name of the block to put.
     * @return      This instance of the packet builder, for chaining.
     */
    public PacketBuilder putInt64(String name) {
        NumericBlock block = new NumericBlock(NumericType.INT64);
        blocks.put(name, block);
        return this;
    }

    /**
     * Puts a numeric long block into the packet.
     *
     * @param name  The name of the block to put.
     * @param l     The long value to put into the blocks.
     * @return      This instance of the packet builder, for chaining.
     */
    public PacketBuilder putInt64(String name, long l) {
        NumericBlock block = new NumericBlock(l, NumericType.INT64);
        blocks.put(name, block);
        return this;
    }

    /**
     * Puts a string block into the packet.
     *
     * @param name  The name of the block to put.
     * @return      This instance of the packet builder, for chaining.
     */
    public PacketBuilder putString(String name) {
        StringBlock block = new StringBlock();
        blocks.put(name, block);
        return this;
    }

    /**
     * Puts a string block into the packet.
     *
     * @param name  The name of the block to put.
     * @param value The value of the string.
     * @return      This instance of the packet builder, for chaining.
     */
    public PacketBuilder putString(String name, String value) {
        StringBlock block = new StringBlock(value);
        blocks.put(name, block);
        return this;
    }

    /**
     * Converts the information provided by this builder to a packet.
     *
     * @return The created packet.
     */
    public Packet toPacket() {
        return new Packet(descriptor, blocks);
    }
}
