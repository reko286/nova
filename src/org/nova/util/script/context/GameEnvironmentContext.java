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

package org.nova.util.script.context;

import org.nova.event.EventHandlerChainDecorator;
import org.nova.util.script.EnvironmentContext;

/**
 * Created by Hadyn Richard
 */
public final class GameEnvironmentContext extends EnvironmentContext {

    /**
     * The packet decoder event handler chain decorator.
     */
    private EventHandlerChainDecorator packetDecoderChainDecorator;

    /**
     * Constructs a new {@link GameEnvironmentContext};
     */
    public GameEnvironmentContext() {}

    /**
     * Sets the packet decoder event handler chain decorator.
     *
     * @param decorator The event handler chain decorator.
     */
    public void setPacketDecoderChainDecorator(EventHandlerChainDecorator decorator) {
        packetDecoderChainDecorator = decorator;
    }
}
