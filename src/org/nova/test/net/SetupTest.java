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

package org.nova.test.net;

import org.nova.event.EventHandler;
import org.nova.event.EventHandlerChain;
import org.nova.event.EventHandlerChainContext;
import org.nova.io.PacketDecoderParser;
import org.nova.io.PacketParser;
import org.nova.net.ClientPool;
import org.nova.net.Reactor;
import org.nova.net.event.PacketParsedEvent;
import org.nova.net.event.SocketChannelEvent;
import org.nova.net.event.handler.ClientInputEventHandler;
import org.nova.net.event.handler.SocketChannelAcceptEventHandler;
import org.nova.net.event.handler.SocketChannelReadEventHandler;
import org.nova.util.meta.PacketData;
import org.nova.util.meta.PacketDecoderData;

import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.List;
import java.util.Map;

/**
 * Created by Hadyn Richard
 */
public final class SetupTest {

    /** The inline test handler for printing out opted interest */
    private static class TestSocketChannelEventHandler extends EventHandler<SocketChannelEvent> {

        @Override
        public void handle(SocketChannelEvent event, EventHandlerChainContext<SocketChannelEvent> context) {
            System.out.println("Interest opted from socket channel [interest=" + event.getInterest() + "]");
            System.out.println(System.currentTimeMillis());
        }
    }

    /** The inline test handler for printing out parsed packets */
    private static class TestPacketParsedEventHandler extends EventHandler<PacketParsedEvent> {

        @Override
        public void handle(PacketParsedEvent event, EventHandlerChainContext<PacketParsedEvent> context) {
            System.out.println("Packet parsed [name=" + event.getPacket().getName() + "]");
            System.out.println(System.currentTimeMillis());
        }
    }

    /**
     * The main entry point of the program.
     *
     * @param args  The command line arguments.
     */
    public static void main(String[] args) {
        try {

            PacketDecoderParser decoderParser = new PacketDecoderParser(new FileInputStream("./data/decoders.xml"));
            PacketParser packetParser = new PacketParser(new FileInputStream("./data/packets.xml"));

            /* Parse the packet and packet decoder data */
            List<PacketDecoderData> packetDecoderDataList = decoderParser.parse();
            Map<String, PacketData> packetDataMap = packetParser.parse();

            /* Create the reactor */
            Selector selector = Selector.open();
            Reactor reactor = new Reactor(selector);

            ClientPool pool = new ClientPool();

            SocketChannelReadEventHandler handler = new SocketChannelReadEventHandler(pool);
            
            EventHandlerChain<PacketParsedEvent> chain = new EventHandlerChain<PacketParsedEvent>();
            chain.addToBack(new TestPacketParsedEventHandler());

            for(PacketDecoderData data : packetDecoderDataList) {
                PacketData packetData = packetDataMap.get(data.getPacket());

                ClientInputEventHandler inputHandler = new ClientInputEventHandler(data.create(packetData), chain);
                handler.getChain().addToBack(inputHandler);
            }

            /* Register all the handlers to the reactor */
            reactor.registerHandler(SocketChannelEvent.class, new TestSocketChannelEventHandler());
            reactor.registerHandler(SocketChannelEvent.class, new SocketChannelAcceptEventHandler(pool));
            reactor.registerHandler(SocketChannelEvent.class, handler);

            /* Open, configure, and then bind the socket channel to a port */
            ServerSocketChannel channel = ServerSocketChannel.open();
            channel.configureBlocking(false);
            channel.bind(new InetSocketAddress(40001));

            /* Register the selector */
            channel.register(selector, SelectionKey.OP_ACCEPT);
            
            System.out.println("Server socket channel bound to port 40001");

            /* Loop the logic, killing our CPU woohoo! */
            for(;;) {

                reactor.dispatchEvents(null);   /* The executor service is not really needed */
            }

        } catch(Throwable t) {
            System.err.println("Eeek! Something went wrong");
        }
    }
}
