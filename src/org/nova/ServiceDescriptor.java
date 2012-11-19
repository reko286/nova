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

/**
 * Runelocus Development
 * Created by Hadyn Richard
 */
public final class ServiceDescriptor {
    
    /**
     * The name of the service.
     */
    private final String name;
    
    /**
     * The id of the service.
     */
    private final int id;
    
    /**
     * Constructs a new {@link ServerDescriptor};
     * 
     * @param name  The name of the service. 
     * @param id    The id of the service.
     */
    public ServiceDescriptor(String name, int id) {
        this.name = name;
        this.id = id;
    }
    
    /**
     * Gets the name of the service.
     * 
     * @return  The name.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Gets the id of the service.
     * 
     * @return  The id.
     */
    public int getId() {
        return id;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj == null || !(obj instanceof ServiceDescriptor)) {
            return false;
        }
        
        ServiceDescriptor descriptor = (ServiceDescriptor) obj;
        return descriptor.id == id && descriptor.name.equals(name);
    }

    @Override
    public int hashCode() {
        return name.hashCode() * 31 ^ id;
    }
}