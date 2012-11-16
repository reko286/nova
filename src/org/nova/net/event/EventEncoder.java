/**
 * Copyright Evelus, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Hadyn Richard (sini@evel.us), July 2012
 */

package org.nova.net.event;

import org.nova.event.Event;
import org.nova.net.Packet;

/**
 * Evelus Development
 * Created by Hadyn Richard
 * 
 * The base class for all event to packet encoders.
 */
public interface EventEncoder<T extends Event> {
    
    /**
     * Encodes an event into a packet.
     * 
     * @param event The event to encode.
     * @return      The encoded packet.
     */
    public Packet encode(T event);

}
