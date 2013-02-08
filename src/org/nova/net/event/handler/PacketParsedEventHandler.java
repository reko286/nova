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
import org.nova.net.event.PacketParsedEvent;
import org.nova.net.packet.Packet;
import org.nova.net.packet.codec.PacketEventDecoder;

/**
 * Created by Hadyn Richard
 */
public final class PacketParsedEventHandler extends EventHandler<PacketParsedEvent> {

    /**
     * The service manager for this handler.
     */
    private ServiceManager serviceManager;

    /**
     * The name of the packet to decorate for.
     */
    private String packetName;

    /**
     * The decoder to use to decode the packet into an event.
     */
    private PacketEventDecoder decoder;

    /**
     * Constructs a new {@link PacketParsedEventHandler};
     *
     * @param serviceManager    The service manager for this handler.
     * @param packetName        The name of the packet to handle.
     * @param decoder           The packet to event decoder for this handler.
     */
    public PacketParsedEventHandler(ServiceManager serviceManager, String packetName, PacketEventDecoder decoder) {
        this.serviceManager = serviceManager;
        this.packetName = packetName;
        this.decoder = decoder;
    }

    @Override
    public void handle(PacketParsedEvent event, EventHandlerChainContext<PacketParsedEvent> context) {

        Packet packet = event.getPacket();

        /* Check if the packet is the correct one to decorate for */
        if(!packet.getName().equals(packetName)) {
            return;
        }

        /* Stop the event from being propagated further */
        context.stop();
        
        /* Decode the event from the provided packet */
        Event decodedEvent = decoder.decode(event);

        /* Do not go any further if the decoded event is null */
        if(decodedEvent == null) {
            return;
        }

        /* Get the service that the client is currently being handled by */
        Service service = serviceManager.get(event.getClient().getServiceType());

        /* Dispatch the decoded event */
        service.getDispatcher().handleEvent(decodedEvent);
    }
}
