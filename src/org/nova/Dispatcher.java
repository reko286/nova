/**
 * Copyright Evelus, All Rights Reserved Unauthorized copying of this file, via
 * any medium is strictly prohibited Proprietary and confidential Written by
 * Hadyn Richard (sini@evel.us), July 2012
 */
package org.nova;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;

import org.nova.event.Event;
import org.nova.event.EventHandler;
import org.nova.event.EventHandlerChain;

/**
 * Runelocus Development 
 * Created by Trey, Hadyn Richard
 */
public abstract class Dispatcher {

    /**
     * The map for each of the event handler chains.
     */
    private Map<Class<? extends Event>, EventHandlerChain<?>> handlerChains;
    
    /**
     * The set for each of the required events for this reactor to function properly.
     */
    private Set<Class<? extends Event>> requiredEvents;

    /**
     * Checks if the reactor contains all the required event handler chains.
     * 
     * @return  If the reactor contains each of the required event handler chains.
     */
    public boolean hasMetRequirements() {
        for (Class<? extends Event> eventClass : requiredEvents) {
            Class<?> checkClass = eventClass;
            while(checkClass != null) {
                if (!handlerChains.containsKey(checkClass)) {
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
    
    /**
     * Gets the required events set.
     * 
     * @return  The required events.
     */
    public Set<Class<? extends Event>> getRequiredEvents() {
        return requiredEvents;
    }
    
    
    public void addRequiredEvent(Class<? extends Event> eventClass) {
    	requiredEvents.add(eventClass);
    }
    
    public boolean requiresEvent(Class<? extends Event> eventClass) {
    	return requiredEvents.contains(eventClass);
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
    public abstract void dispatchEvents(ExecutorService executor);
}