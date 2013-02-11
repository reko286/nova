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

import org.nova.core.ServiceType;
import org.nova.net.packet.Packet;
import org.nova.net.packet.codec.PacketDecoderState;
import org.nova.net.packet.codec.PacketEncoderState;
import org.nova.net.packet.codec.PacketDecoderState.Stage;

import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Hadyn Richard
 */
public final class Client {

    /**
     * The selection key for the client.
     */
    private SelectionKey selectionKey;

    /**
     * The packet handler for the client.
     */
    private PacketHandler packetHandler;

    /**
     * The message handler for the client.
     */
    private MessageHandler messageHandler;

    /**
     * The input buffer for the client.
     */
    private ByteBuffer inputBuffer;

    /**
     * The output buffer for the client.
     */
    private ByteBuffer outputBuffer;

    /**
     * The packet decoder state for this client.
     */
    private PacketDecoderState decoderState;

    /**
     * The packet encoder state for this client.
     */
    private PacketEncoderState encoderState;

    /**
     * The service type for this client.
     */
    private ServiceType serviceType;

    /**
     * The list of disconnection listeners for the client.
     */
    private List<DisconnectListener> disconnectListeners;

    /**
     * Constructs a new {@link Client};
     *
     * @param selectionKey      The selection key for this client.
     * @param packetHandler     The packet handler for this client.
     * @param messageHandler    The message handler for this client.
     */
    public Client(SelectionKey selectionKey, PacketHandler packetHandler, MessageHandler messageHandler) {
        decoderState = new PacketDecoderState();
        inputBuffer = ByteBuffer.allocate(5000);
        outputBuffer = ByteBuffer.allocate(5000);

        /* Initialize the decoder state */
        decoderState.setBuffer(inputBuffer);
        decoderState.setStage(Stage.AWAITING_ID);

        this.selectionKey = selectionKey;
        this.packetHandler = packetHandler;
        this.messageHandler = messageHandler;
    }

    /**
     * Gets the packet handler for the client.
     *
     * @return  The packet handler.
     */
    public PacketHandler getPacketHandler() {
        return packetHandler;
    }

    /**
     * Gets the message handler for the client.
     *
     * @return  The message handler.
     */
    public MessageHandler getMessageHandler() {
        return messageHandler;
    }

    /**
     * Gets the packet encoder state.
     *
     * @return  The encoder state.
     */
    public PacketEncoderState getEncoderState() {
        return encoderState;
    }

    /**
     * Gets the packet decoder state.
     *
     * @return  The decoder state.
     */
    public PacketDecoderState getDecoderState() {
        return decoderState;
    }

    /**
     * Gets the output buffer for this client.
     *
     * @return  The output buffer.
     */
    public ByteBuffer getOutputBuffer() {
        return outputBuffer;
    }

    /**
     * Gets the input buffer for this client.
     *
     * @return  The input buffer.
     */
    public ByteBuffer getInputBuffer() {
        return inputBuffer;
    }

    /**
     * Sets the service type for this client.
     *
     * @param serviceType   The service type.
     */
    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    /**
     * Gets the service type for this client.
     *
     * @return  The service type.
     */
    public ServiceType getServiceType() {
        return serviceType;
    }

    /**
     * Adds a disconnect listener to this client.
     *
     * @param listener  The listener to add.
     */
    public void addDisconnectListener(DisconnectListener listener) {
        disconnectListeners.add(listener);
    }

    /**
     * Removes a disconnect listener from this client.
     *
     * @param listener  The listener to remove.
     */
    public void removeDisconnectListener(DisconnectListener listener) {
        disconnectListeners.remove(listener);
    }

    /**
     * Writes a message to the client.
     *
     * @param message   The message to write.
     */
    public void writeMessage(Message message) {

        /* Encode the message into a packet and check if it was successfully encoded */
        Packet encodedPacket = messageHandler.encode(message);
        if(encodedPacket == null) {
            return;
        }
        
        /* Set the encoder states packet */
        encoderState.setPacket(encodedPacket);

        /* Encode the packet into a buffer and check if it was successfully encoded */
        ByteBuffer buffer = packetHandler.encode(encoderState);
        if(buffer == null) {
            return;
        }

        /* Put the buffer into the output buffer  */
        outputBuffer.put(buffer);

        /* Mark the selection key for write interest */
        selectionKey.interestOps(selectionKey.interestOps() | SelectionKey.OP_WRITE);
    }

    /**
     * Disconnects the client.
     */
    public void disconnect() {

        /* Alert the listeners that the client is being disconnected */
        for(DisconnectListener listeners : disconnectListeners) {
            listeners.onDisconnect(this);
        }

        try {
            SelectableChannel channel = selectionKey.channel();
            channel.close();
        } catch(Throwable t) {
            throw new RuntimeException(t); // This shouldn't happen but if it does let off a bit of a warning
        }
    }
}