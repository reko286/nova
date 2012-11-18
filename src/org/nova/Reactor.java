/**
 * Copyright Evelus, All Rights Reserved Unauthorized copying of this file, via
 * any medium is strictly prohibited Proprietary and confidential Written by
 * Hadyn Richard (sini@evel.us), July 2012
 */
package org.nova;

import java.util.Map;
import java.util.Set;
import org.nova.event.Event;
import org.nova.event.EventHandler;
import org.nova.event.EventHandlerChain;

/**
 * Runelocus Development 
 * Created by Trey
 */
public abstract class Reactor {

    protected Map<Class<? extends Event>, EventHandlerChain> handlerMap;
    protected Set<Class<? extends Event>> requiredEvents;

    public Set<Class<? extends Event>> getRequiredEvents() {
        return requiredEvents;
    }

    /**
     * Checks if the reactor contains all the required event handler chains.
     * 
     * @return  If the reactor contains each of the required event handler chains.
     */
    public boolean hasMetRequirements() {
        for (Class<? extends Event> eventClass : requiredEvents) {
            Class checkClass = eventClass;
            while(checkClass != null) {
                if (!handlerMap.containsKey(checkClass)) {
                    return false;
                }
                checkClass = checkClass.getSuperclass();
            }
        }
        return true;
    }

    /**
     * Registers an event handler to this reactor.
     * 
     * @param eventClass    The class of the event to register the event for.
     * @param handler       The event handler to register.
     * @return              If the register map already contained an event handler
     *                      chain for the specified event.
     */
    public boolean registerHandler(Class<? extends Event> eventClass, EventHandler handler) {
        if(!handlerMap.containsKey(eventClass)) {
            EventHandlerChain chain = new EventHandlerChain();
            chain.addToBack(handler);
            handlerMap.put(eventClass, chain);
            return false;
        } else {
            EventHandlerChain chain = handlerMap.get(eventClass);
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
    public EventHandlerChain unregisterChain(Class<? extends Event> eventClass) {
        return handlerMap.remove(eventClass);
    }

    /**
     * Gets the handler chain for an event.
     * 
     * @param event The event to get the handler chain for.
     * @return      The handler chain.
     */
    public EventHandlerChain getHandlerChainFor(Event event) {
        return handlerMap.get(event.getClass());
    }

    /**
     * Handles an event.
     * 
     * @param event The event to handle.
     */
    public abstract void handleEvent(Event event);

    /**
     * Dispatches all the events for this reactor.
     */
    public abstract void dispatchEvents();
}