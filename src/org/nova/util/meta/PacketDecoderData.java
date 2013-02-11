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

import org.nova.net.packet.codec.PacketDecoder;

/**
 * Created by Hadyn Richard
 */
public final class PacketDecoderData extends PacketCodecData<PacketDecoder> {

    /**
     * Constructs a new {@link PacketDecoderData};
     *
     * @param id            The id associated with the decoder.
     * @param packet        The name of the packet associated with the decoder.
     */
    public PacketDecoderData(int id, String packet) {
        super(id, packet);
    }

    /**
     * Creates a new packet decoder from the packet codec information.
     *
     * @param packetData    The packet data to use to create the decoder with.
     * @return              The created packet decoder.
     */
    public PacketDecoder create(PacketData packetData) {

        /* Create the packet decoder */
        PacketDecoder decoder = new PacketDecoder(id, packetData);

        for(Block block : blocks) {

            /* Add the block to the decoder */
            decoder.addBlock(block.name);

            /* Check if a transformer needs to be used */
            if(block.useTransformer) {

                /* Add the transformer to the decoder */
                decoder.addTransformer(block.name, block.transformer);
            }
        }

        return decoder;
    }
}
