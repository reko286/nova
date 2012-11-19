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
import java.util.Iterator;
import java.util.Map;
import org.nova.ServerContext;

/**
 * Runelocus Development
 * Created by Hadyn Richard
 */
public final class ServiceManager {
    
    /**
     * The services registered to this service manager.
     */
    private Map<ServiceDescriptor, Service> services;
    
    /**
     * Constructs a new {@link ServiceManager};
     */
    public ServiceManager() {
        services = new HashMap<ServiceDescriptor, Service>();
    }
    
    /**
     * Registers a service to this service manager.
     * 
     * @param descriptor    The descriptor of the service.
     * @param service       The service to register.
     * @return              The previously registered service for the specified
     *                      descriptor if there was one.
     */
    public Service register(ServiceDescriptor descriptor, Service service) {
        return services.put(descriptor, service);
    }
    
    /**
     * Gets a service for a specific service descriptor.
     * 
     * @param descriptor    The descriptor of the service.
     * @return              The service.
     */
    public Service get(ServiceDescriptor descriptor) {
        return services.get(descriptor);
    }
    
    /**
     * Unregisters a service from this service manager.
     * 
     * @param descriptor    The descriptor of the service to unregister.
     * @return              The unregistered service for the descriptor.
     */
    public Service unregister(ServiceDescriptor descriptor) {
        return services.remove(descriptor);
    }
    
    /**
     * Sets the context for each of the services.
     * 
     * @param context   The context.
     */
    public void setContext(ServerContext context) {
        Iterator<Service> iterator = services.values().iterator();
        while(iterator.hasNext()) {
            Service service = iterator.next();
            service.setContext(context);
        }
    }
    
    /**
     * Stops and unregisters each of the services.
     */
    public void stopAndUnregisterAll() {
        Iterator<Service> iterator = services.values().iterator();
        while(iterator.hasNext()) {
            Service service = iterator.next();
            service.stop();
        }
        services.clear();
    }
}