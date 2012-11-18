/**
 * Copyright Evelus, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Hadyn Richard (sini@evel.us), July 2012
 */

package org.nova.event;

/**
 * Evelus Development
 * Created by Hadyn Richard
 */
public abstract class EventHandler<T extends Event> {
    
    /**
     * Handles an event.
     * 
     * @param event     The event to handle.
     * @param context   The event handler chain context.
     */
    public abstract void handle(T event, EventHandlerChainContext<T> context);

}
