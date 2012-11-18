/**
 * Copyright Evelus, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Hadyn Richard (sini@evel.us), July 2012
 */

package org.nova.event;

import org.nova.event.Event;
import java.util.Deque;
import java.util.LinkedList;

/**
 * Evelus Development
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
