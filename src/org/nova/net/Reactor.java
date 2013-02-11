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
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import org.nova.core.Dispatcher;
import org.nova.event.Event;
import org.nova.event.EventHandlerChain;
import org.nova.event.EventHandlerChainContext;
import org.nova.net.event.SocketChannelEvent;
import org.nova.net.event.SocketChannelEvent.SocketInterest;
import org.nova.net.task.PropagationTask;
import org.nova.task.PartitionedWorkQueue;

/**
 * Created by Trey, Hadyn Richard
 */
public final class Reactor extends Dispatcher {

    /**
     * The amount of tasks per each worker when propagating events.
     */
    private static final int TASKS_PER_WORKER = 50;

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
    public void dispatchEvents(ExecutorService executor) {
        try {

            /* Create the work queue with the default amount of tasks per worker */
            PartitionedWorkQueue workQueue = new PartitionedWorkQueue(TASKS_PER_WORKER);

            selector.select();

            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            while (it.hasNext()) {

                /* Get and remove the next selection key */
                SelectionKey key = it.next();
                it.remove();

                if (key.isValid()) {

                    List<Event> events = new LinkedList<Event>();

                    if (key.isAcceptable()) {

                        /* Accept the socket channel */
                        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
                        SocketChannel socketChannel = serverChannel.accept();

                        /* Check if the accepted socket channel is valid */
                        if(socketChannel == null) {
                            continue;
                        }
                        
                        socketChannel.configureBlocking(false);

                        /* Create the socket channel event with the accept socket interest */
                        events.add(new SocketChannelEvent(socketChannel, selector, key, SocketInterest.ACCEPT));
                    }

                    if (key.isReadable()) {
                        
                        SocketChannel socketChannel = (SocketChannel) key.channel();

                        /* Create the socket channel event with the read socket interest */
                        events.add(new SocketChannelEvent(socketChannel, selector, key, SocketInterest.READ));
                    }

                    if (key.isWritable()) {

                        SocketChannel socketChannel = (SocketChannel) key.channel();

                        /* Create the socket channel event with the write socket interest */
                        events.add(new SocketChannelEvent(socketChannel, selector, key, SocketInterest.WRITE));
                    }

                    /* Propagate the events down the event handler chain if a valid interest was specified */
                    if(!events.isEmpty()) {
                        for(Event event : events) {

                            /* Get and check if the event handler chain is valid */
                            EventHandlerChain chain = getHandlerChainFor(event);
                            if(chain == null) {
                                continue;
                            }

                            /* Create the event handler chain context */
                            EventHandlerChainContext context = chain.createNewEventHandlerChainContext(event);

                            /* Add a new propagation task to the work queue */
                            workQueue.add(new PropagationTask(context));
                        }
                    }
                }
            }

            /* Execute all the queued tasks */
            workQueue.execute(executor);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** Just handle the event immediately */
    @Override
    public void handleEvent(Event event) {
        getHandlerChainFor(event).createNewEventHandlerChainContext(event).doAll();
    }
}
