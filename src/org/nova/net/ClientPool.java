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

import java.nio.channels.SelectionKey;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hadyn Richard
 *
 * Notes:
 *
 *          Is the SelectionKey the best thing to use?
 */
public final class ClientPool {

    /**
     * The factory to create new clients with.
     */
    private ClientFactory factory;

    /**
     * The map of clients.
     */
    private Map<SelectionKey, Client> clients;

    /**
     * Constructs a new {@link ClientPool};
     *
     * @param factory   The client factory to create clients with.
     */
    public ClientPool(ClientFactory factory) {
        clients = new HashMap<SelectionKey, Client>();

        this.factory = factory;
    }

    /**
     * Creates a client and registers to this client pool.
     *
     * @param key   The selection key to create the client with.
     * @return      The created client.
     */
    public Client create(SelectionKey key) {

        /* Create and register the client */
        Client createdClient = factory.create(key);
        clients.put(key, createdClient);

        return createdClient;
    }

    /**
     * Gets a client from its selection key.
     *
     * @param key   The selection key of the client to get.
     * @return      The client for the selection key or null if client does not exist.
     */
    public Client getClient(SelectionKey key) {
        return clients.get(key);
    }
}
