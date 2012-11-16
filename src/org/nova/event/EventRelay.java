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
public abstract class EventRelay<T extends Event> {
    
    /**
     * Processes the event.
     * 
     * @param event The event to process.
     * @return      The newly created event to relay further.
     */
    public abstract Event process(T event);

}
