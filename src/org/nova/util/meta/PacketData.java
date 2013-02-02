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

package org.nova.util.meta;

import org.nova.net.packet.Packet;
import org.nova.net.packet.PacketBuilder;
import org.nova.net.packet.PacketVariable;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Hadyn Richard
 */
public final class PacketData {

    /**
     * The numeric representation for a variety byte sized packet.
     */
    public static final int VAR_BYTE = -1;

    /**
     * The numeric representation for a variety short sized packet.
     */
    public static final int VAR_SHORT = -2;

    /**
     * The name of the packet.
     */
    private String name;

    /**
     * The size of the packet.
     */
    private int size;
    
    /**
     * The list of variables for the packet.
     */
    private List<PacketVariable> variables;

    /**
     * Constructs a new {@link PacketData};
     * 
     * @param name  The name of the packet.
     * @param size  The size of the packet.
     */
    public PacketData(String name, int size) {
        this.name = name;
        this.size = size;
        
        variables = new LinkedList<PacketVariable>();
    }

    /**
     * Gets the size of the packet.
     *
     * @return  The size.
     */
    public int getSize() {
        return size;
    }

    /**
     * Gets if this packet is variety short or byte sized.
     *
     * @return  If the packet is variety sized.
     */
    public boolean isVarietySized() {
        return size == VAR_BYTE || size == VAR_SHORT;
    }

    /**
     * Gets if this packet is variety byte sized.
     *
     * @return  If the packet is variety byte sized.
     */
    public boolean isVarietyByteSized() {
        return size == VAR_BYTE;
    }

    /**
     * Gets if this packet is variety short sized.
     *
     * @return  If the packet is variety short sized.
     */
    public boolean isVarietyShortSized() {
        return size == VAR_SHORT;
    }

    /**
     * Adds a packet variable to the variable list.
     *
     * @param variable  The variable to add.
     */
    public void addVariable(PacketVariable variable) {
        variables.add(variable);
    }

    /**
     * Gets the list of variables for the packet.
     *
     * @return  The variables.
     */
    public List<PacketVariable> getVariables() {
        return variables;
    }

    /**
     * Creates a new packet from this scaffold.
     *
     * @return             The created packet.
     */
    public Packet create() {

        /* Create a new packet build from the descriptor */
        PacketBuilder builder = new PacketBuilder(name);

        /* Create a block for each variable */
        for(PacketVariable variable : variables) {
            switch(variable.getType()) {

                case INT8:
                    builder.putInt8(variable.getName());
                    break;

                case INT16:
                    builder.putInt16(variable.getName());
                    break;
                
                case INT24:
                    builder.putInt24(variable.getName());
                    break;

                case INT32:
                    builder.putInt32(variable.getName());
                    break;

                case INT64:
                    builder.putInt64(variable.getName());
                    break;

                case STRING:
                    builder.putString(variable.getName());
                    break;
            }
        }

        return builder.toPacket();
    }
}
