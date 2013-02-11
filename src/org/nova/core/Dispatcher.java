/**
 * Copyright (c) 2012, Hadyn Richard
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy 
 * of this software and associated documentation files (the "Software"), to deal 
 * in the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in 
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL 
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN 
 * THE SOFTWARE.
 */

package org.nova.core;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import org.nova.event.Event;
import org.nova.event.EventHandler;
import org.nova.event.EventHandlerChain;

/**
 * Created by Trey, Hadyn Richard
 *
 * TODO: Add in the required event chains set!
 */
public abstract class Dispatcher {

    /**
     * The map for each of the event handler chains.
     */
    private Map<Class<? extends Event>, EventHandlerChain<?>> handlerChains;

    /**
     * Constructs a new {@link Dispatcher};
     */
    public Dispatcher() {
        handlerChains = new HashMap<Class<? extends Event>, EventHandlerChain<?>>();
    }

    /**
     * Handles an event.
     *
     * @param event The event to decorate.
     */
    public abstract void handleEvent(Event event);

    /**
     * Dispatches all the events for this reactor.
     *
     * @param executor  The executor service to dispatch events to.
     */
    public abstract void dispatchEvents(ExecutorService executor);
    
    /**
     * Registers an event handler to this reactor.
     * 
     * @param eventClass    The class of the event to register the event for.
     * @param handler       The event handler to register.
     * @return              If the register map already contained an event handler
     *                      chain for the specified event.
     */
    public boolean registerHandler(Class<? extends Event> eventClass, EventHandler<?> handler) {
        if(!handlerChains.containsKey(eventClass)) {
            EventHandlerChain chain = new EventHandlerChain();
            chain.addToBack(handler);
            handlerChains.put(eventClass, chain);
            return false;
        } else {
            EventHandlerChain chain = handlerChains.get(eventClass);
            chain.addToBack(handler);
            return true;
        }
    }

    /**
     * Unregisters a chain of event handlers for an event.
     * 
     * @param eventClass    The class of the event to unregister the handlers for.
     * @return              The unregistered event handler chain.
     */
    public EventHandlerChain<?> unregisterChain(Class<? extends Event> eventClass) {
        return handlerChains.remove(eventClass);
    }

    /**
     * Gets the handler chain for an event.
     * 
     * @param event The event to get the handler chain for.
     * @return      The handler chain.
     */
    public EventHandlerChain getHandlerChainFor(Event event) {
        return handlerChains.get(event.getClass());
    }
}