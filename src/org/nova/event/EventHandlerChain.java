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


package org.nova.event;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Created by Hadyn Richard
 */
public final class EventHandlerChain<T extends Event> {
    
    /**
     * The double ended queue of event handlers that make up the chain.
     */
    private Deque<EventHandler<T>> handlerChain;
    
    /**
     * Constructs a new {@link EventHandlerChain};
     */
    public EventHandlerChain() {
        handlerChain = new LinkedList<EventHandler<T>>();
    }
    
    /**
     * Adds an event handler to the front of the handler chain.
     * 
     * @param handler   The handler to add to the chain.
     */
    public void addToFront(EventHandler<T> handler) {
        handlerChain.addFirst(handler);
    }
    
    /**
     * Adds an event handler to the back of the handler chain.
     * 
     * @param handler   The handler to add to the chain.
     */
    public void addToBack(EventHandler<T> handler) {
        handlerChain.addLast(handler);
    }
    
    /**
     * Removes an event handler from the event handler chain.
     * 
     * @param handler   The event handler to remove.
     */
    public void remove(EventHandler<T> handler) {
        handlerChain.remove(handler);
    }
    
    /**
     * Creates a new {@link EventHandlerChainContext}.
     * 
     * @param   event   The event to create the chain context for.
     * @return          The created {@link EventHandlerChainContext}.
     */
    public EventHandlerChainContext<T> createNewEventHandlerChainContext(T event) {
        return new EventHandlerChainContext<T>(event, handlerChain.iterator());
    }
}
