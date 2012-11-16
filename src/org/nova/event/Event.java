/**
 * Copyright Evelus, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Hadyn Richard (sini@evel.us), July 2012
 */

package org.nova.event;

import java.util.EventObject;

/**
 * Evelus Development
 * Created by Hadyn Richard
 * 
 * The base class for all events.
 */
public abstract class Event extends EventObject {
    
    /**
     * Constructs a new {@link Event};
     * 
     * @param source    The source of the event.
     */
    public Event(Object source) {
        super(source);
    }
}
