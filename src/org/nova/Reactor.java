/**
 * Copyright Evelus, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Hadyn Richard (sini@evel.us), July 2012
 */

package org.nova;

import java.util.Map;
import org.nova.event.Event;
import org.nova.event.EventHandlerChain;
import org.nova.event.EventHandlerChainContext;

/**
 * Evelus Development
 * Created by Hadyn Richard
 */
public class Reactor {
    
    private Map<Object, EventHandlerChain> registeredHandlerChains;
    
    public void handleEvent(Event event) {
        EventHandlerChain chain = null;//Somehow get the event handler chain
        EventHandlerChainContext context = chain.createNewEventHandlerChainContext(event);
        //submit to engine to handle the event down the chain
        
    }

}
