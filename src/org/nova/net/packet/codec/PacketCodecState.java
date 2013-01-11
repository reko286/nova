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

package org.nova.net.packet.codec;

import org.nova.net.ISAACCipher;

/**
 * Created by Hadyn Richard
 *
 * Notes:
 *
 *          Set useCipher to true when the cipher is initialized?
 *          WARNING: Packet encoding is not async when using the cipher!
 */
public abstract class PacketCodecState {

    /**
     * The cipher used to spoof packet ids.
     *
     * TODO: Decide if its a good idea to use keep the cipher initialized at a null state
     */
    private ISAACCipher cipher;

    /**
     * The flag for if the codec should use the cipher to spoof the packet ids.
     */
    private boolean useCipher;

    /**
     * Constructs a new {@link PacketCodecState};
     *
     * Protected access to prevent inline method construction.
     */
    protected PacketCodecState() {}

    /**
     * Sets the flag for if the cipher should be used to spoof packet ids.
     *
     * @param useCipher The use cipher flag.
     */
    public void setUseCipher(boolean useCipher) {
        this.useCipher = useCipher;
    }

    /**
     * Gets if the cipher should be used to spoof the packet ids.
     *
     * @return  If the cipher is to be used to spoof the packet ids.
     */
    public boolean useCipher() {
        return useCipher;
    }

    /**
     * Set the cipher to be used when spoofing packet ids.
     *
     * @param cipher    The cipher to use.
     */
    public void setCipher(ISAACCipher cipher) {
        this.cipher = cipher;
    }

    /**
     * Gets the cipher to use to spoof packet ids.
     *
     * @return  The cipher.
     */
    public ISAACCipher getCipher() {
        return cipher;
    }
}
