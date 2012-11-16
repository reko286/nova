/**
 * Copyright Evelus, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Hadyn Richard (sini@evel.us), July 2012
 */

package org.nova.event;

import java.util.Iterator;

/**
 * Evelus Development
 * Created by Hadyn Richard
 */
public final class EventRelayChainContext<T extends Event> {
    
    /**
     * The event to propagate down the chain.
     */
    private Event event;
    
    /**
     * The event relay iterator.
     */
    private Iterator<EventRelay> iterator;
    
    /**
     * The event relay sink.
     */
    private EventRelaySink sink;
    
    /**
     * The flag for if the propagation was aborted.
     */
    private boolean aborted;
    
    /**
     * Constructs a new {@link EventRelayChainContext};
     */
    public EventRelayChainContext(T event, Iterator<EventRelay> iterator, EventRelaySink sink) {
        this.event = event;
        this.iterator = iterator;
        this.sink = sink;
    }
    
    /**
     * Checks to see if the state of this context is valid.
     */
    private void checkState() {
        if(!iterator.hasNext() || aborted) {
            throw new IllegalStateException();
        }   
    }
    
    /**
     * Processes the next relays for the event.
     * 
     * @throws IllegalStateException    If the iterator does not have another
     *                                  relay to process.
     */
    public void doNext() {
        checkState();
        
        EventRelay relay = iterator.next();
        event = relay.process(event);
        
        if(event == null) {
            aborted = true;
            return;
        }
        
        if(isFinished()) {
            sink.handle(event);
        }
    }
    
    /**
     * Processes the remaining relays for the event.
     * 
     * @throws IllegalStateException    If the iterator does not have another
     *                                  relay to process.
     */
    public void doAll() {
        checkState();
        
        while(iterator.hasNext()) {
            EventRelay relay = iterator.next();
            event = relay.process(event);
            
            if(event == null) {
                aborted = true;
                return;
            }
        }
        
        sink.handle(event);
    }
 
    /**
     * Gets if the event has finished propagating down the chain.
     * 
     * @return  If the event has finished propagating.
     */
    public boolean isFinished() {
        return iterator.hasNext() || aborted;
    }
}
