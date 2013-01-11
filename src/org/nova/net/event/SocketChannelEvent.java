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

import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * Created by Hadyn Richard
 */
public final class SocketChannelEvent extends Event {

    /**
     * The enumeration for different interests that can be indicated from a socket channel.
     */
    public enum SocketInterest {

        /**
         * The accept interest for socket channel.
         */
        ACCEPT,

        /**
         * The read interest for a socket channel.
         */
        READ,

        /**
         * The write interest for a socket channel.
         */
        WRITE
    }

    /**
     * The socket channel to read from.
     */
    private SocketChannel socketChannel;

    /**
     * The selector which the read interest was indicated from.
     */
    private Selector selector;

    /**
     * The interest for the socket.
     */
    private SocketInterest interest;

    /**
     * Constructs a new {@link SocketChannelEvent};
     *
     * @param socketChannel The socket channel that triggered the event.
     * @param selector      The selector which the interest was indicated from.
     * @param interest      The interest of the socket.
     */
    public SocketChannelEvent(SocketChannel socketChannel, Selector selector, SocketInterest interest) {
        this.socketChannel = socketChannel;
        this.selector = selector;
        this.interest = interest;
    }
}
