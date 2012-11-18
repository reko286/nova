/**
 * Copyright Evelus, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Hadyn Richard (sini@evel.us), July 2012
 */

package org.nova.event.impl;

import org.nova.event.Event;
import org.nova.event.EventHandler;
import org.nova.event.EventHandlerChain;
import org.nova.event.EventHandlerChainContext;

/**
 * Evelus Development
 * Created by Hadyn Richard
 * 
 * Used for propagating an event down a event handler chain in a nested context.
 */
public final class EventChainHandler<T extends Event> extends EventHandler<T> {
    
    /**
     * The event chain to propagate an event down with.
     */
    private EventHandlerChain chain;
    
    /**
     * Constructs a new {@link EventChainHandler};
     * 
     * @param chain The event chain to propagate the event down with.
     */
    public EventChainHandler(EventHandlerChain chain) {
        this.chain = chain;
    }

    @Override
    public void handle(T event, EventHandlerChainContext context) {
        EventHandlerChainContext<T> nestedContext = chain.createNewEventHandlerChainContext(event);
        nestedContext.doAll();
    }
}
