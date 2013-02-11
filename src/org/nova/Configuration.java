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

import org.nova.util.script.Script;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Hadyn Richard
 */
public final class Configuration {

    /**
     * The scripts to be ran on startup on the global server script environment.
     */
    private List<Script> scripts;

    /**
     * The id of the world.
     */
    private int worldId;

    /**
     * The base port value.
     */
    private int basePort;

    /**
     * Constructs a new {@link Configuration};
     */
    public Configuration() {
        scripts = new LinkedList<Script>();
    }

    /**
     * Adds a script.
     *
     * @param script    The script to be added.
     */
    public void addScript(Script script) {
        scripts.add(script);
    }

    /**
     * Get the scripts.
     *
     * @return  The scripts.
     */
    public List<Script> getScripts() {
        return scripts;
    }

    /**
     * Sets the world id.
     *
     * @param worldId   The world id.
     */
    public void setWorldId(int worldId) {
        this.worldId = worldId;
    }

    /**
     * Gets the world id.
     * 
     * @return  The world id.
     */
    public int getWorldId() {
        return worldId;
    }

    /**
     * Set the base port value.
     *
     * @param basePort  The base port.
     */
    public void setBasePort(int basePort) {
        this.basePort = basePort;
    }

    /**
     * Gets the base port value.
     *
     * @return  The base port.
     */
    public int getBasePort() {
        return basePort;
    }
}
