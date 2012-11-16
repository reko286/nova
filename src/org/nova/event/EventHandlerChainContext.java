/**
 * Copyright Evelus, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Hadyn Richard (sini@evel.us), July 2012
 */

package org.nova.event;

import org.nova.event.Event;
import java.util.Iterator;

/**
 * Evelus Development
 * Created by Hadyn Richard
 * 
 * Used to help propagate an event down a event handler chain. 
 */
public final class EventHandlerChainContext<T extends Event> {
    
    /**
     * The event to propagate down the event handler chain.
     */
    private final Event event;
    
    /**
     * The event handler iterator.
     */
    private final Iterator<EventHandler> iterator;
    
    /**
     * Flag for if handler has been requested to stop.
     */
    private boolean stopped;
    
    /**
     * Constructs a new {@link EventHandlerChainContext};
     * 
     * @param event     The event to handle in this context.
     * @param iterator  The event handler iterator.
     */
    public EventHandlerChainContext(T event, Iterator<EventHandler> iterator) {
        this.event = event;
        this.iterator = iterator;
    }
    
    /**
     * Checks to see if the state of this context is valid.
     */
    private void checkState() {
        if(!iterator.hasNext() || stopped) {
            throw new IllegalStateException();
        }   
    }
    
    /**
     * Alerts this context to stop processing handlers past this point.
     */
    public void stop() {
        stopped = true;
    }
    
    /**
     * Processes the next handler for the event.
     * 
     * @throws IllegalStateException    If the iterator does not have another
     *                                  handler to process, or if the context
     *                                  has been to requested to stop processing
     *                                  handlers.
     */
    public void doNext() {
        checkState();
        
        EventHandler handler = iterator.next();
        handler.handle(event, this);
    }
    
    /**
     * Processes the remaining handlers for the event.
     * 
     * @throws IllegalStateException    If the iterator does not have another
     *                                  handler to process, or if the context
     *                                  has been to requested to stop processing
     *                                  handlers.
     */
    public void doAll() {
        checkState();
        
        while(iterator.hasNext() || !stopped) {
            EventHandler handler = iterator.next();
            handler.handle(event, this);
        }
    }
    
    /**
     * Gets if the event is finished propagating down the event handler chain.
     * 
     * @return  If the event has finished propegating.
     */
    public boolean isFinished() {
        return !iterator.hasNext() || stopped;
    }
}