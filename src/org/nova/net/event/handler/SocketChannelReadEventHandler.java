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

import org.nova.core.Service;
import org.nova.core.ServiceManager;
import org.nova.event.Event;
import org.nova.event.EventHandler;
import org.nova.event.EventHandlerChainContext;
import org.nova.net.Client;
import org.nova.net.ClientPool;
import org.nova.net.ISAACCipher;
import org.nova.net.Message;
import org.nova.net.event.MessageDecodedEvent;
import org.nova.net.event.SocketChannelEvent;
import org.nova.net.packet.Packet;
import org.nova.net.packet.codec.PacketDecoderState;
import org.nova.net.packet.codec.PacketDecoderState.Stage;

import java.io.IOException;
import java.nio.ByteBuffer;
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
     * The service manager for this handler.
     */
    private ServiceManager serviceManager;

    /**
     * Constructs a new {@link SocketChannelReadEventHandler};
     *
     * @param clientPool        The client pool to grab clients from.
     * @param serviceManager    The service manager for this handler.
     */
    public SocketChannelReadEventHandler(ClientPool clientPool, ServiceManager serviceManager) {
        this.clientPool = clientPool;
        this.serviceManager = serviceManager;
    }

    @Override
    public void handle(SocketChannelEvent event, EventHandlerChainContext<SocketChannelEvent> context) {

        /* Check if the interest is correct to be handled */
        if(!event.getInterest().equals(SocketChannelEvent.SocketInterest.READ)) {
            return;
        }

        /* Get the client for the selection key */
        Client client = clientPool.getClient(event.getSelectionKey());

        /* TODO: Figure out what we want to do if the client is null or even if this is possible */

        /* Stop the context from propagating further */
        context.stop();
        
        /* Read the bytes from the socket channel to the client input buffer */
        try {
            SocketChannel channel = event.getSource();

            /* Read to the input buffer and rewind it afterward */
            ByteBuffer buffer = client.getInputBuffer();
            channel.read(buffer);
            buffer.rewind();
        } catch(IOException ex) {

            /* Disconnect the client */
            client.disconnect();
            return;
        }

        for(;;) {

            /* Check if the packet id needs to be determined */
            PacketDecoderState state = client.getDecoderState();
            if(state.getStage().equals(PacketDecoderState.Stage.AWAITING_ID)) {

                /* Check if we can parse the packet id from the buffer */
                ByteBuffer buffer = state.getBuffer();
                if(buffer.remaining() < 1) {
                    break;
                }

                int id = buffer.get() & 0xFF;

                /* Check if the id needs to be deciphered */
                if(state.useCipher()) {

                    ISAACCipher cipher = state.getCipher();
                    if(cipher == null) {
                        throw new IllegalStateException("cipher cannot be null");
                    }

                    id = id - cipher.getNextValue() & 0xFF;
                }

                /* Set the id and that we are now awaiting bytes */
                state.setDecoderId(id);
                state.setStage(PacketDecoderState.Stage.AWAITING_BYTES);
            }

            /* Decode the packet and check if it successfully decoded */
            Packet packet = client.getPacketHandler().decode(state);
            if(packet == null) {
                break;
            }

            /* Get the buffer that was used to parse the packet and compact, and rewind it */
            ByteBuffer buffer = state.getBuffer();
            buffer.compact().rewind();

            /* Alert that we are now awaiting for an id again */
            state.setStage(Stage.AWAITING_ID);

            /* Decode the message from the provided packet */
            Message decodedMessage = client.getMessageHandler().decode(packet);

            /* Do not go any further if the decoded message is null */
            if(decodedMessage == null) {
                return;
            }

            /* Get the service that the client is currently being handled by */
            Service service = serviceManager.get(client.getServiceType());

            /* Dispatch the message event */
            Event messageEvent = new MessageDecodedEvent(client, decodedMessage);
            service.getDispatcher().handleEvent(messageEvent);
        }
    }
}
