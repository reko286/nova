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

import org.nova.event.Event;
import org.nova.event.EventHandler;
import org.nova.event.EventHandlerChain;
import org.nova.event.EventHandlerChainContext;
import org.nova.net.Client;
import org.nova.net.ClientPool;
import org.nova.net.event.ClientInputEvent;
import org.nova.net.event.SocketChannelEvent;

import java.io.IOException;
import java.nio.channels.SocketChannel;

/**
 * Created by Hadyn Richard
 */
public final class SocketChannelReadEventHandler extends EventHandler<SocketChannelEvent> {

    /**
     * The client pool to grab the clients from.
     */
    private ClientPool clientPool;

    /**
     * The chain to propagate the event for the bytes being read down to.
     */
    private EventHandlerChain chain;

    /**
     * Constructs a new {@link SocketChannelReadEventHandler};
     *
     * @param clientPool    The client pool to grab clients from.
     * @param chain         The event handler chain to use to propagate further events down to.
     */
    public SocketChannelReadEventHandler(ClientPool clientPool, EventHandlerChain<ClientInputEvent> chain) {
        this.clientPool = clientPool;
        this.chain = chain;
    }

    @Override
    public void handle(SocketChannelEvent event, EventHandlerChainContext<SocketChannelEvent> context) {

        /* Check if the interest is correct to be handled */
        if(!event.getInterest().equals(SocketChannelEvent.SocketInterest.READ)) {
            return;
        }

        /* Stop the context from propagating further */
        context.stop();

        /* Get the client for the selection key */
        Client client = clientPool.getClient(event.getSelectionKey());

        /* TODO: Figure out what we want to do if the client is null or even if this is possible */
        
        /* Read the bytes from the socket channel to the client input buffer */
        try {
            SocketChannel channel = event.getSocketChannel();
            channel.read(client.getInputBuffer());
        } catch(IOException ex) {
            /* TODO: Handle a disconnect here */
        }

        /* Create the input event and propagate it down the chain */
        Event inputEvent = new ClientInputEvent(client);
        chain.createNewEventHandlerChainContext(inputEvent).doAll();
    }
}
