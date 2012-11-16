/**
 * Copyright Evelus, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Hadyn Richard (sini@evel.us), July 2012
 */

package org.nova.net.event;

import org.nova.event.Event;
import org.nova.net.Packet;
import org.nova.net.Packet;

/**
 * Evelus Development
 * Created by Hadyn Richard
 * 
 * The base class for all event to packet encoders.
 */
public abstract class EventEncoder<T extends Event> {
    
    /**
     * Encodes an event into a packet.
     * 
     * @param event The event to encode.
     * @return      The encoded packet.
     */
    public abstract Packet encode(T event);

}
