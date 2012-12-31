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

package org.nova.net.packet;

import org.nova.net.PacketBlock;

import java.nio.ByteBuffer;

/**
 * Created by Hadyn Richard
 */
public final class NumericBlock extends PacketBlock {

    /**
     * Enumeration for each of the numeric types.
     */
    public enum NumericType {

        /*
         * Representation for a byte value.
         */
        BYTE,

        /**
         * Representation for a short value.
         */
        SHORT,

        /**
         * Representation for an integer value.
         */
        INTEGER,

        /**
         * Representation for a long value.
         */
        LONG
    }

    /**
     * The value of this numeric block.
     */
    private Number value;

    /**
     * The type of variable to encode and decode this block as.
     */
    private NumericType type;

    /**
     * Constructs a new {@link NumericBlock};
     *
     * @param value The value of the numeric block.
     * @param type  The type of the numeric block.
     */
    public NumericBlock(Number value, NumericType type) {
        this.value = value;
        this.type = type;
    }

    @Override
    public void encode(ByteBuffer buffer) {

        /* Put the value into the buffer */
        switch(type) {

            case BYTE:
                buffer.put(value.byteValue());
                break;

            case SHORT:
                buffer.putShort(value.shortValue());
                break;

            case INTEGER:
                buffer.putInt(value.intValue());
                break;

            case LONG:
                buffer.putLong(value.longValue());
                break;
        }
    }
}