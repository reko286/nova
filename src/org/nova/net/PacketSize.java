/**
 * Copyright Evelus, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Hadyn Richard (sini@evel.us), July 2012
 */

package org.nova.net;

/**
 * Evelus Development
 * Created by Hadyn Richard
 * 
 * Each of the enumerations for a packet's possible size.
 */
public enum PacketSize {
    
    /**
     * Static sized packets only have one length.
     */
    STATIC,
    
    /**
     * Variable byte length packets have a range in size from 0 to 255, inclusive.
     */
    VAR_BYTE,
    
    /**
     * Variable short length packets have a range in size from 0 to 65535, inclusive.
     */
    VAR_SHORT

}