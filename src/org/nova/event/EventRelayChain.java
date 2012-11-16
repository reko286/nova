/**
 * Copyright Evelus, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Hadyn Richard (sini@evel.us), July 2012
 */

package org.nova.event;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Evelus Development
 * Created by Hadyn Richard
 */
public final class EventRelayChain<T extends Event> {
    
    /**
     * The double ended queue of event relays that make up the chain.
     */
    private Queue<EventRelay> relayChain;
    
    /**
     * The event relay sink for this chain.
     */
    private EventRelaySink sink;
            
    /**
     * Constructs a new {@link EventRelayChain};
     * 
     * @param sink  The event relay sink.
     */
    public EventRelayChain(EventRelaySink sink) {
        relayChain = new LinkedList<>();
    }
    
    /**
     * Appends a relay to the chain.
     * 
     * @param relay The relay to append.
     */
    public void append(EventRelay relay) {
        relayChain.add(relay);
    }
    
    /**
     * Creates a new {@link EventRelayChainContext};
     * 
     * @param event The event to create the chain context for.
     * @return      The created {@link EventRelayChainContext}.  
     */
    public EventRelayChainContext<T> createNewEventRelayChainContext(T event) {
        return new EventRelayChainContext(event, relayChain.iterator(), sink);
    } 
}