/**
 * Copyright (c) 2012, Hadyn Richard
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.nova.net;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import org.nova.core.Dispatcher;
import org.nova.event.Event;
import org.nova.net.event.AcceptEvent;
import org.nova.net.event.ReadEvent;
import org.nova.net.event.WriteEvent;

public class Reactor extends Dispatcher {

    /**
     * The selector to use for the reactor.
     */
    private Selector selector;

    /**
     * Constructs a new {@link Reactor};
     * 
     * @param selector  The selector for the reactor.
     */
    public Reactor(Selector selector) {
        this.selector = selector;
    }

    @Override
    public void handleEvent(Event event) {
        getHandlerChainFor(event).createNewEventHandlerChainContext(event).doAll();
    }

    @Override
    public void dispatchEvents(ExecutorService executor) {
        try {
            selector.select();
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            while (it.hasNext()) {
                SelectionKey key = it.next();
                it.remove();
                if (key.isValid()) {
                    if (key.isAcceptable()) {

                        /* Accept the socket channel */
                        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
                        SocketChannel socketChannel = serverChannel.accept();

                        /* Check if the accepted socket channel is valid */
                        if(socketChannel == null) {
                            continue;
                        }

                        /* Create the accept event and propagate it down the event handler chain */
                        Event event = new AcceptEvent(socketChannel, selector);
                        getHandlerChainFor(event).createNewEventHandlerChainContext(event).doAll();
                    } else if (key.isReadable()) {
                        
                        SocketChannel socketChannel = (SocketChannel) key.channel();

                        /* Create the read event and propagate it down the event handler chain */
                        Event event = new ReadEvent(socketChannel, selector);
                        getHandlerChainFor(event).createNewEventHandlerChainContext(event).doAll();
                    } else if (key.isWritable()) {

                        SocketChannel socketChannel = (SocketChannel) key.channel();

                        /* Create the write event and propagate it down the event handler chain */
                        Event event = new WriteEvent(socketChannel, selector);
                        getHandlerChainFor(event).createNewEventHandlerChainContext(event).doAll();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
