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

package org.nova.event;

import java.util.Iterator;

/**
 * Evelus Development
 * Created by Hadyn Richard
 * 
 * Used to help propagate an event down a event handler chain. 
 */
public final class EventHandlerChainContext<T extends Event> {
    
    /**
     * The event to propagate down the event handler chain.
     */
    private final T event;
    
    /**
     * The event handler iterator.
     */
    private final Iterator<EventHandler<T>> iterator;
    
    /**
     * Flag for if handler has been requested to stop.
     */
    private boolean stopped;
    
    /**
     * Constructs a new {@link EventHandlerChainContext};
     * 
     * @param event     The event to decorate in this context.
     * @param iterator  The event handler iterator.
     */
    public EventHandlerChainContext(T event, Iterator<EventHandler<T>> iterator) {
        this.event = event;
        this.iterator = iterator;
    }
    
    /**
     * Checks to see if the state of this context is valid.
     */
    private void checkState() {
        if(!iterator.hasNext() && stopped) {
            throw new IllegalStateException();
        }   
    }
    
    /**
     * Alerts this context to stop processing handlers past this point.
     */
    public void stop() {
        stopped = true;
    }
    
    /**
     * Processes the next handler for the event.
     * 
     * @throws IllegalStateException    If the iterator does not have another
     *                                  handler to process, or if the context
     *                                  has been to requested to stop processing
     *                                  handlers.
     */
    public void doNext() {
        checkState();
        
        EventHandler<T> handler = iterator.next();
        handler.handle(event, this);
    }
    
    /**
     * Processes the remaining handlers for the event.
     * 
     * @throws IllegalStateException    If the iterator does not have another
     *                                  handler to process, or if the context
     *                                  has been to requested to stop processing
     *                                  handlers.
     */
    public void doAll() {
        checkState();
        
        while(iterator.hasNext() && !stopped) {
            EventHandler<T> handler = iterator.next();
            handler.handle(event, this);
        }
    }
    
    /**
     * Gets if the event is finished propagating down the event handler chain.
     * 
     * @return  If the event has finished propegating.
     */
    public boolean isFinished() {
        return !iterator.hasNext() || stopped;
    }
}