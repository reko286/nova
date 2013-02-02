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

/**
 * Created by Hadyn Richard
 */
public enum NumericType {

    /*
    * Representation for a byte value.
    */
    INT8,

    /**
     * Representation for a short value.
     */
    INT16,

    /**
     * Representation for a medium integer value.
     */
    INT24,

    /**
     * Representation for an integer value.
     */
    INT32,

    /**
     * Representation for a long value.
     */
    INT64;

    /**
     * Gets the length in bytes of a numeric type.
     *
     * @param type  The numeric type to get the length for.
     * @return      The length in bytes.
     */
    public static int getByteLength(NumericType type)  {
        switch(type) {

            case INT8:
                return 1;

            case INT16:
                return 2;

            case INT24:
                return 3;

            case INT32:
                return 4;

            case INT64:
                return 8;

            default:
                throw new RuntimeException("Unhandled numeric type");
        }
    }
}