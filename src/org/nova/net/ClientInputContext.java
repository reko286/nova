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

package org.nova.net;

/**
 * Created by Hadyn Richard
 *
 * Notes:
 *
 *          The reason why I did not merge the methods/fields of this class with the client input event was because
 *          an event is not supposed to be reusable. The use of the context object allows for the context to be reused
 *          between multiple event objects and hold state values for that process.
 */
public final class ClientInputContext {

    /**
     * The flag for if the data should be continued to be parsed.
     */
    private boolean loop;

    /**
     * Constructs a new {@link ClientInputContext};
     */
    public ClientInputContext() {}

    /**
     * Sets if the data parsed from the client should be continued to be parsed
     * by the client input event handler chain.
     *
     * @param loop  The loop flag.
     */
    public void setLoop(boolean loop) {
        this.loop = loop;
    }

    /**
     * Gets if the data parsed from the client should be continued to be parsed
     * by the client input event handler chain.
     *
     * @return  The loop flag.
     */
    public boolean getLoop() {
        return loop;
    }
}
