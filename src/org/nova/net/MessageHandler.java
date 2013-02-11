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

import org.nova.net.packet.Packet;
import org.nova.net.packet.codec.MessageDecoder;
import org.nova.net.packet.codec.MessageEncoder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hadyn Richard
 */
public final class MessageHandler {

    /**
     * The map of encoders to turn messages into packets.
     */
    private Map<Class, MessageEncoder> encoders;

    /**
     * The map of decoders to turn packets into messages.
     */
    private Map<String, MessageDecoder> decoders;

    /**
     * Constructs a new {@link MessageHandler};
     */
    public MessageHandler() {
        encoders = new HashMap<Class, MessageEncoder>();
        decoders = new HashMap<String, MessageDecoder>();
    }

    /**
     * Register an encoder to this message handler.
     *
     * @param messageClass  The class of the message to register for.
     * @param encoder       The encoder to register.
     */
    public void registerEncoder(Class<? extends Message> messageClass, MessageEncoder encoder) {
        encoders.put(messageClass, encoder);
    }

    /**
     * Encodes a message into a packet.
     *
     * @param message   The message to encode into a packet.
     * @return          The encoded packet from the message.
     */
    public Packet encode(Message message) {

        /* Check if an encoder exists for the message */
        Class<? extends Message> messageClass = message.getClass();
        if(encoders.containsKey(messageClass)) {
            return null;
        }

        /* Encode the message into a packet */
        MessageEncoder encoder = encoders.get(messageClass);
        return encoder.encode(message);
    }

    /**
     * Registers a decoder to this message handler.
     *
     * @param packetName    The name of the packet to register the decoder for.
     * @param decoder       The message decoder to register.
     */
    public void registerDecoder(String packetName, MessageDecoder decoder) {
        decoders.put(packetName, decoder);
    }

    /**
     * Decodes a message from a packet.
     *
     * @param packet    The packet to decode the message from.
     * @return          The decoded message.
     */
    public Message decode(Packet packet) {
        
        /* Check if the encoder exists for the specified packet */
        String packetName = packet.getName();
        if(!decoders.containsKey(packetName)) {
            return null;
        }

        /* Decode the packet into a message */
        MessageDecoder decoder = decoders.get(packetName);
        return decoder.decode(packet);
    }
}
