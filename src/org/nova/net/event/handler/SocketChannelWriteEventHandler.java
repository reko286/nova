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

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;

/**
 * Created by Hadyn Richard
 */
public final class SocketChannelWriteEventHandler extends EventHandler<SocketChannelEvent> {

    /**
     * The client pool to grab the clients from.
     */
    private ClientPool clientPool;

    /**
     * Constructs a new {@link SocketChannelReadEventHandler};
     *
     * @param clientPool    The client pool to grab clients from.
     */
    public SocketChannelWriteEventHandler(ClientPool clientPool) {
        this.clientPool = clientPool;
    }

    @Override
    public void handle(SocketChannelEvent event, EventHandlerChainContext<SocketChannelEvent> context) {

        /* Check if the interest is correct to be handled */
        if(!event.getInterest().equals(SocketChannelEvent.SocketInterest.WRITE)) {
            return;
        }

        /* Get the client for the selection key */
        Client client = clientPool.getClient(event.getSelectionKey());

        /* TODO: Figure out what we want to do if the client is null or even if this is possible */

        /* Stop the context from propagating further */
        context.stop();

        /* Write the output buffer to the socket channel */
        ByteBuffer outputBuffer = client.getOutputBuffer();

        try {
            event.getSource().write(outputBuffer);
        } catch(IOException ex) {

            /* Something went wrong and we need to disconnect the client */
            client.disconnect();
            return;
        }

        /* Compact the output buffer */
        outputBuffer.compact();

        /* Remove the write interest if there are no longer any messages/bytes to write */
        if(!outputBuffer.hasRemaining()) {
            SelectionKey key = event.getSelectionKey();
            key.interestOps(key.interestOps() & ~SelectionKey.OP_WRITE);
        }
    }
}
