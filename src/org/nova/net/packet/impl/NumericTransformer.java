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

package org.nova.net.packet.impl;

import org.nova.net.packet.NumericBlock;
import org.nova.net.packet.NumericType;
import org.nova.net.packet.PacketBlock;
import org.nova.net.packet.Transformer;

import java.nio.ByteBuffer;

/* Yes nested class static imports, bite me */
import static org.nova.net.packet.impl.NumericTransformer.ByteOrder.*;
import static org.nova.net.packet.impl.NumericTransformer.Translation.*;

import static org.nova.net.packet.NumericType.*;

/**
 * Created by Hadyn Richard
 */
public final class NumericTransformer extends Transformer<Number, NumericBlock> {

    /**
     * The enumeration for each of the translations.
     */
    public static enum Translation {

        /**
         * No translation will affect the variable.
         */
        NONE,

        /**
         * 128 will be added to the value.
         */
        A,

        /**
         * The value will be inverted.
         */
        C,

        /**
         * The value will be subtracted from 128.
         */
        S
    }

    /**
     * The enumeration for each of the byte orders.
     */
    public static enum ByteOrder {

        /**
         * Big endian encoded variables will be ordered MSB to LSB.
         */
        BIG,

        /**
         * Little endian encoded variables will be ordered LSB to MSB.
         */
        LITTLE,

        /**
         * Special endianness used for integer variables.
         */
        MIDDLE,

        /**
         * Special endianness used for integer variables.
         */
        INVERSE_MIDDLE
    }

    /**
     * The translation to use to use to encode the value with.
     */
    private Translation translation;

    /**
     * The byte order to use to encode the value with.
     */
    private ByteOrder order;

    /**
     * Constructs a new {@link NumericTransformer};
     *
     * @param translation   The translation to use with the LSB of the values to encode and decode.
     * @param order         The byte order of the values to encode and decode.
     */
    public NumericTransformer(Translation translation, ByteOrder order) {
        this.translation = translation;
        this.order = order;

        /* Check if the translation was provided */
        if(translation == null) {
            throw new IllegalStateException("translation cannot be null");
        }

        /* Check if the byte order was provided */
        if(order == null) {
            throw new IllegalStateException("order cannot be null");
        }
    }

    /**
     * Check the state to assure that the provided type is compatible.
     *
     * @param type  The given type to encode.
     */
    private void checkState(NumericType type) {

        /* Check to assure that if the order is middle that the numeric type is an integer */
        if(order == MIDDLE || order == INVERSE_MIDDLE) {
            if(type != INT32) {
                throw new IllegalStateException("Middle and inverse middle endianness is reserved for integers only");
            }
        }
    }

    @Override
    public Number encode(NumericBlock block) {
        
        Number input = block.getValue();
        NumericType type = block.getType();

        checkState(type);
        
        int byteLength = NumericType.getByteLength(type);
        ByteBuffer buffer = ByteBuffer.allocate(byteLength);

        /* Store the number into the buffer */
        switch(type) {

            case INT8:
                buffer.put(input.byteValue());
                break;

            case INT16:
                buffer.putShort(input.shortValue());
                break;
            
            case INT24:
                buffer.putShort((short) (input.intValue() >> 8));
                buffer.put(input.byteValue());
                break;

            case INT32:

                /* So that we do not have to manipulate the variable later */
                if(order != MIDDLE) {
                    buffer.putInt(input.intValue());
                } else {
                    int value = input.intValue();
                    buffer.putShort((short) (value >> 16));
                    buffer.putShort((short) value);
                }
                break;

            case INT64:
                buffer.putLong(input.longValue());
                break;
        }

        /* Apply the translation to the LSB */
        int lsbPosition = byteLength - 1;
        switch(translation) {

            case A:
                buffer.put(lsbPosition, (byte) (buffer.get(lsbPosition) + 128));
                break;

            case C:
                buffer.put(lsbPosition, (byte) (-buffer.get(lsbPosition)));
                break;

            case S:
                buffer.put(lsbPosition, (byte) (128 - buffer.get(lsbPosition)));
                break;
        }
        
        /* Invert the bytes in the buffer if we have to */
        if(order == LITTLE || order == INVERSE_MIDDLE) {
            byte[] bytes = buffer.array();
            buffer.flip();
            
            for(int i = bytes.length - 1; i >= 0; i--) {
                buffer.put(bytes[i]);
            }
        }

        /* Flip the buffer for reading */
        buffer.flip();
        
        /* Read the new number from the buffer */
        Number newNumber = null;
        switch(type) {

            case INT8:
                newNumber = buffer.get();
                break;

            case INT16:
                newNumber = buffer.getShort();
                break;

            case INT24:
                newNumber = buffer.getShort() << 8 | buffer.get();
                break;

            case INT32:
                newNumber = buffer.getInt();
                break;

            case INT64:
                newNumber = buffer.getLong();
                break;
        }
        
        return newNumber;
    }


    @Override
    public Number decode(NumericBlock block) {

        Number input = block.getValue();
        NumericType type = block.getType();

        int byteLength = NumericType.getByteLength(type);
        ByteBuffer buffer = ByteBuffer.allocate(byteLength);

        /* Store the number into the buffer */
        switch(type) {

            case INT8:
                buffer.put(input.byteValue());
                break;

            case INT16:
                buffer.putShort(input.shortValue());
                break;

            case INT24:
                buffer.putShort((short) (input.intValue() >> 8));
                buffer.put(input.byteValue());
                break;

            case INT32:
                buffer.putInt(input.intValue());
                break;

            case INT64:
                buffer.putLong(input.longValue());
                break;
        }

        /* Invert the bytes in the buffer if we have to */
        if(order == LITTLE || order == INVERSE_MIDDLE) {
            byte[] bytes = buffer.array();
            buffer.flip();

            for(int i = bytes.length - 1; i >= 0; i--) {
                buffer.put(bytes[i]);
            }
        }

        /* Flip the buffer for reading */
        buffer.flip();

        /* Apply the translation to the LSB */
        int lsbPosition = byteLength - 1;
        switch(translation) {

            case A:
                buffer.put(lsbPosition, (byte) (buffer.get(lsbPosition) - 128));
                break;

            case C:
                buffer.put(lsbPosition, (byte) (-buffer.get(lsbPosition)));
                break;

            case S:
                buffer.put(lsbPosition, (byte) (128 - buffer.get(lsbPosition)));
                break;
        }

        /* Read the new number from the buffer */
        Number newNumber = null;
        switch(type) {

            case INT8:
                newNumber = buffer.get();
                break;

            case INT16:
                newNumber = buffer.getShort();
                break;

            case INT24:
                newNumber = buffer.getShort() << 8 | buffer.get();
                break;

            case INT32:
                if(order != MIDDLE && order != INVERSE_MIDDLE) {
                    newNumber = buffer.getInt();
                } else {
                    newNumber = buffer.getShort() | buffer.getShort() << 16;
                }
                break;

            case INT64:
                newNumber = buffer.getLong();
                break;
        }

        return newNumber;
    }
}
