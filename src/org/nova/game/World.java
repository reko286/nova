/**
 * Copyright Evelus, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Hadyn Richard (sini@evel.us), July 2012
 */

package org.nova.game;

/**
 * Evelus Development
 * Created by Hadyn Richard
 */
public final class World {

    /**
     * The singleton instance of this world.
     */
    private static final World instance = new World();
    
    /**
     * Gets the instance of the world.
     *
     * @return  The world instance. 
     */
    public static World getWorld() {
        return instance;
    }
}
