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

package org.nova.net.packet.codec.impl;

import org.nova.net.packet.codec.PacketCodecState;

import java.nio.ByteBuffer;

/**
 * Created by Hadyn Richard
 */
public final class PacketDecoderState extends PacketCodecState {

    /**
     * The enumerated stage for decoding the packet.
     */
    public enum Stage {

        /**
         * The stage representing that the id of the packet has yet to be read.
         */
        AWAITING_ID,

        /**
         * The stage representing that the bytes of the packet have yet to be read.
         */
        AWAITING_BYTES

    }

    /**
     * The id for the decoder of the packet being parsed.
     */
    private int decoderId;

    /**
     * The buffer to use to decode the packet from.
     */
    private ByteBuffer buffer;

    /**
     * The current stage for parsing the packet.
     */
    private Stage stage;
    
    /**
     * Constructs a new {@link PacketDecoderState};
     */
    public PacketDecoderState() {}

    /**
     * Sets the id for the decoder of the packet currently being parsed.
     *
     * @param decoderId    The packet id.
     */
    public void setDecoderId(int decoderId) {
        this.decoderId = decoderId;
    }

    /**
     * Gets the packet decoder id.
     *
     * @return  The packet decoder id.
     */
    public int getDecoderId() {
        return decoderId;
    }

    /**
     * Sets the buffer to use to encode the packets from.
     *
     * @param buffer    The buffer to use.
     */
    public void setBuffer(ByteBuffer buffer) {
        this.buffer = buffer;
    }

    /**
     * Gets the buffer to use to decode the packets from.
     *
     * @return  The buffer.
     */
    public ByteBuffer getBuffer() {
        return buffer;
    }

    /**
     * Sets the stage of the decoder.
     * 
     * @param stage The stage.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Gets the stage of the decoder.
     *
     * @return The stage.
     */
    public Stage getStage() {
        return stage;
    }
}
