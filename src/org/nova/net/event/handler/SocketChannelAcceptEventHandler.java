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

package org.nova.net.event.handler;

import org.nova.event.EventHandler;
import org.nova.event.EventHandlerChainContext;
import org.nova.net.Client;
import org.nova.net.ClientPool;
import org.nova.net.event.SocketChannelEvent;

import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * Created by Hadyn Richard
 */
public final class SocketChannelAcceptEventHandler extends EventHandler<SocketChannelEvent> {

    /**
     * The client pool to register clients to.
     */
    private ClientPool clientPool;

    /**
     * Constructs a new {@link SocketChannelAcceptEventHandler};
     * 
     * @param clientPool    The client pool to register the clients to.
     */
    public SocketChannelAcceptEventHandler(ClientPool clientPool) {
        this.clientPool = clientPool;
    }

    @Override
    public void handle(SocketChannelEvent event, EventHandlerChainContext<SocketChannelEvent> context) {

        /* Check if the interest is correct to be handled */
        if(!event.getInterest().equals(SocketChannelEvent.SocketInterest.ACCEPT)) {
            return;
        }

        /* Stop the context from propagating further */
        context.stop();

        /* Get the selection key to register the client as */
        SelectionKey key = null;
        try {
            SocketChannel channel = event.getSocketChannel();
            channel.register(event.getSelector(), SelectionKey.OP_READ);
        } catch (ClosedChannelException e) {

            /* For some reason the channel was closed and we should return */
            return;
        }

        /* Register the client to the client pool */
        Client client = new Client(key);
        clientPool.register(client, key);
    }
}
