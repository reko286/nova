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

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/**
 * Created by Hadyn Richard
 *
 * Notes:
 *
 *          Default character set is ASCII
 *          <del>Let terminator character change in a constructor parameter?</del>
 *              In different Runescape revisions, the terminator character changes between '\n' and '\0'.
 */
public final class StringBlock extends PacketBlock<String> {

    /**
     * The value of the string block.
     */
    private String value;

    /**
     * The character used to terminate strings.
     */
    private char terminatorChar;

    /**
     * Constructs a new {@link StringBlock};
     * The default value of this block is an empty string.
     */
    public StringBlock() {
        this("");
    }

    /**
     * Constructs a new {@link StringBlock};
     * 
     * @param value The value of the string block.
     */
    public StringBlock(String value) {
        this(value, '\0');
    }

    /**
     * Constructs a new {@link StringBlock};
     *
     * @param value             The value of the string block.
     * @param terminatorChar    The character used to denote the end of a string.
     */
    public StringBlock(String value, char terminatorChar) {
        super(value);

        this.terminatorChar = terminatorChar;
    }

    @Override
    public void encode(ByteBuffer buffer) {
        buffer.put(value.getBytes());
        buffer.put((byte) terminatorChar);                // Null character terminated
    }

    @Override
    public void decode(ByteBuffer buffer) {
        StringBuilder builder = new StringBuilder();

        /* Append each of the characters to the builder */
        char val = '\0';
        while((val = (char) buffer.get()) != terminatorChar) {
            builder.append(val);
        }

        value = builder.toString();
    }

    @Override
    public int getLength()  {
        try {
            return value.getBytes("ASCII").length + 1;
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);             // Something has gone terribly wrong
        }
    }
}
