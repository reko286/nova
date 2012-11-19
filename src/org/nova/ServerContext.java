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

package org.nova;

import org.nova.core.ServiceDescriptor;
import org.nova.core.ServiceManager;
import org.nova.core.Service;

/**
 * Runelocus Development
 * Created by Hadyn Richard
 */
public final class ServerContext {
    
    /**
     * The service manager for the server context.
     */
    private ServiceManager serviceManager;
    
    /**
     * Constructs a new {@link ServerContext};
     * 
     * @param serviceManager    The service manager. 
     */
    public ServerContext(ServiceManager serviceManager) {
        this.serviceManager = serviceManager;
        serviceManager.setContext(this);
    }
    
    /**
     * Gets a service from its descriptor.
     * 
     * @param descriptor    The service descriptor.
     * @return              The server for the descriptor.
     */
    public Service getService(ServiceDescriptor descriptor) {
        return serviceManager.get(descriptor);
    }
}