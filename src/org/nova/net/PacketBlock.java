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

import org.nova.net.packet.Transformer;
import org.nova.util.Encoder;

import java.nio.ByteBuffer;

/**
 * Created by Hadyn Richard
 *
 * Represents a variable in the packet.
 *
 * Notes:
 *
 *          Check for the compatibility of a transformer?
 *          Move value object lower and set to protected access or use getter?
 *              Subsequently wouldn't have to check for compatibility past this point because of generics.
 */
public abstract class PacketBlock<T> {

    /**
     * The value of the packet block.
     */
    protected T value;

    /**
     * Constructs a new {@link PacketBlock};
     * 
     * @param value The value of the block.
     */
    protected PacketBlock(T value) {
        this.value = value;
    }

    /**
     * Encodes the block to a byte buffer.
     *
     * @param buffer    The buffer to encode the block to.
     */
    public abstract void encode(ByteBuffer buffer);

    /**
     * Decodes the block from a byte buffer.
     *
     * @param buffer    The buffer to decode the block from.
     */
    public abstract void decode(ByteBuffer buffer);

    /**
     * Gets the length of the block.
     *
     * @return  The length.
     */
    public abstract int getLength();

    /**
     * Transforms the internal value of the block by encoding the value.
     *
     * @param transformer The transformer to use to encode the value of the block.
     */
    public final void encodeValue(Transformer<T> transformer) {
        value = transformer.encode(value);
    }

    /**
     * Transforms the internal value of the block by decoding the value.
     *
     * @param transformer   The transformer to use to decode the value of the black.
     */
    public final void decodeValue(Transformer<T> transformer) {
        value = transformer.decode(value);
    }

    /**
     * Gets the value of the block.
     *
     * @return  The value.
     */
    public final T getValue() {
        return value;
    }
}
