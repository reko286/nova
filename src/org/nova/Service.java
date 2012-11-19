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
package org.nova;

import java.util.concurrent.ExecutorService;

/**
 * Evelus Development 
 * Created by Hadyn Richard, Trey
 */
public abstract class Service {
    
    /**
     * The server context for this service.
     */
    private ServerContext context;

    /**
     * The executor for this service.
     */
    private ExecutorService executor;
    
    /**
     * The dispatcher for this service.
     */
    private Dispatcher dispatcher;
    
    /**
     * The flag for if this service is currently running.
     */
    private boolean isRunning;
    
    /**
     * The flag for if this service has stopped running.
     */
    private boolean hasStopped;

    /**
     * Constructs a new {@link Service};
     * 
     * @param context       The server context for this service.
     * @param executor      The executor service for this service.
     * @param dispatcher    The dispatcher for this service.
     */
    public Service(ServerContext context, ExecutorService executor, Dispatcher dispatcher) {
        this.context = context;
        this.executor = executor;
        this.dispatcher = dispatcher;
        this.isRunning = false;
        this.hasStopped = true;
    }

    /**
     * Starts this service.
     */
    public void start() {
        if (dispatcher.hasMetRequirements()) {
            while (isRunning) {
                dispatcher.dispatchEvents(executor);
            }
        }
        hasStopped = true;
    }

    /**
     * Stops this service.
     */
    public void stop() {
        isRunning = false;
    }

    /**
     * Gets if the service is running.
     * 
     * @return  If the service is running.
     */
    public boolean isRunning() {
        return isRunning;
    }

    /**
     * Gets if the service has stopped.
     * 
     * @return  If the service has stopped.
     */
    public boolean hasStopped() {
        return hasStopped;
    }
    
    /**
     * Gets the server context.
     * 
     * @return  The context.
     */
    public ServerContext getContext() {
        return context;
    }
}
