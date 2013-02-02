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
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Hadyn Richard
 */
public final class ServiceManager {

    /**
     * The services registered to this service manager.
     */
    private Map<ServiceType, Service> services;
    
    /**
     * Constructs a new {@link ServiceManager};
     */
    public ServiceManager() {
        services = new HashMap<ServiceType, Service>();
    }

    /**
     * Registers a service to this service manager.
     * 
     * @param type          The type of the service.
     * @param service       The service to register.
     * @return              The previously registered service for the specified
     *                      descriptor if there was one.
     */
    public Service register(ServiceType type, Service service) {
        return services.put(type, service);
    }

    /**
     * Unregisters a service from this service manager.
     *
     * @param type  The type of the service to unregister.
     * @return      The unregistered service for the descriptor.
     */
    public Service unregister(ServiceType type) {
        return services.remove(type);
    }
    
    /**
     * Gets a service from its registered name.
     * 
     * @param type      The name of the service.
     * @return          The service.
     */
    public Service get(ServiceType type) {
        return services.get(type);
    }
}