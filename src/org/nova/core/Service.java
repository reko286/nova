/**
 * Copyright (c) 2012, Hadyn Richard
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.nova.core;

import java.util.concurrent.ExecutorService;

/**
 * Created by Hadyn Richard, Trey
 */
public abstract class Service {

    /**
     * The executor for this service.
     */
    private ExecutorService executor;
    
    /**
     * The dispatcher for this service.
     */
    private Dispatcher dispatcher;

    /**
     * Constructs a new {@link Service};
     *
     * @param executor      The executor service for this service.
     * @param dispatcher    The dispatcher for this service.
     */
    public Service(ExecutorService executor, Dispatcher dispatcher) {
        this.executor = executor;
        this.dispatcher = dispatcher;
    }

    /**
     * Pulses this service.
     */
    public void pulse() {
        dispatcher.dispatchEvents(executor);
    }

    /**
     * Gets the dispatcher for this service.
     *
     * @return  The dispatcher.
     */
    public Dispatcher getDispatcher() {
        return dispatcher;
    }

    /**
     * Gets if the service is running.
     * 
     * @return  If the service is running.
     */
    public boolean isRunning() {
        return false;
    }
}