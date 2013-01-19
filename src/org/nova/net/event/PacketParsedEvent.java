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

package org.nova.net.event;

import org.nova.event.Event;
import org.nova.net.Client;
import org.nova.net.Packet;

/**
 * Created by Hadyn Richard
 */
public final class PacketParsedEvent extends Event {

    /**
     * The client from which the packet was parsed for.
     */
    private Client client;

    /**
     * The packet that was parsed.
     */
    private Packet packet;

    /**
     * Constructs a new {@link PacketParsedEvent};
     *
     * @param client    The client from which the packet was parsed for.
     * @param packet    The packet that was parsed.
     */
    public PacketParsedEvent(Client client, Packet packet) {
        this.client = client;
        this.packet = packet;
    }

    /**
     * Gets the packet that was parsed.
     *
     * @return  The packet.
     */
    public Packet getPacket() {
        return packet;
    }
}
