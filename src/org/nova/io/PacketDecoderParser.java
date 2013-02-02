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

package org.nova.io;

import org.nova.core.ServiceType;
import org.nova.net.packet.impl.NumericTransformer;
import org.nova.net.packet.impl.NumericTransformer.ByteOrder;
import org.nova.net.packet.impl.NumericTransformer.Translation;
import org.nova.util.meta.PacketDecoderData;
import org.nova.util.xml.XMLNode;
import org.nova.util.xml.XMLParser;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Hadyn Richard
 */
public final class PacketDecoderParser {

    /**
     * The XML parser to use to grab the XML file information.
     */
    private XMLParser parser;

    /**
     * The input stream to read the XML file from.
     */
    private InputStream is;

    /**
     * Constructs a new {@link PacketParser};
     *
     * @param is    The input stream to decode the packet decoders from.
     */
    public PacketDecoderParser(InputStream is) throws SAXException {
        this.is = is;

        parser = new XMLParser();
    }

    /**
     * Parses the packet decoder data from the XML file.
     *
     * @return  The list of packet decoder data.
     */
    public List<PacketDecoderData> parse() throws IOException, SAXException {

        List<PacketDecoderData> dataList = new LinkedList<PacketDecoderData>();

        /* Parse the XML file */
        XMLNode rootNode = parser.parse(is);

        /* Check to make sure that the root node is correctly named */
        if(!rootNode.getName().equals("decoders")) {
            throw new IOException("root node name is invalid");
        }

        /* Get each of the decoder declarations */
        List<XMLNode> decoderNodes = rootNode.getChildren("decoder");

        for(XMLNode node : decoderNodes) {

            /* Check if the service node exists */
            if(!node.containsChild("service")) {
                throw new IOException("service not declared");
            }

            /* Get the service node value */
            String serviceName = node.getFirstChild("service").getValue();

            /* Get the service type from the service node value */
            ServiceType service = ServiceType.valueOf(serviceName);

            /* Check if the service is valid */
            if(service == null) {
                throw new IOException("service '" + serviceName + "' is not recognized");
            }

            /* Check if the packet node exists */
            if(!node.containsChild("packet")) {
                throw new IOException("packet not declared");
            }

            /* Get the packet node value */
            String packetName = node.getFirstChild("packet").getValue();

            /* Check if the id node exists */
            if(!node.containsChild("id")) {
                throw new IOException("id not declared");
            }

            /* Parse the id of the decoder */
            int id = Integer.parseInt(node.getFirstChild("id").getValue());

            /* Create the meta data */
            PacketDecoderData data = new PacketDecoderData(service,id, packetName);

            /* Check if the blocks node exists */
            if(!node.containsChild("blocks")) {

                /* Get the blocks node */
                XMLNode blocksNode = node.getFirstChild("blocks");

                /* Get the block nodes */
                List<XMLNode> blockNodes = blocksNode.getChildren("block");

                for(XMLNode block : blockNodes) {

                    /* Check if the name is declared */
                    if(!node.containsChild("name")) {
                        throw new IOException("name not declared for numeric block");
                    }

                    /* Get the name of the block */
                    String name = node.getFirstChild("name").getValue();

                    /* Check if there is a declared numeric transformer */
                    if(node.containsChild("numerictransformer")) {

                        /* Get the numeric transformer node */
                        XMLNode transformerNode = node.getFirstChild("numerictransformer");

                        /* Check if the numeric transformer node is valid */
                        if(!transformerNode.containsChild("order") && !transformerNode.containsChild("translation")) {
                            throw new IOException("invalid numerictransformer node");
                        }

                        /* By default use big endian ordering */
                        ByteOrder order = ByteOrder.BIG;
                        
                        if(transformerNode.containsChild("order")) {
                            
                            /* Get the name of the order */
                            String orderName = transformerNode.getFirstChild("order").getValue();

                            /* Get the byte order from the name of the order */
                            order = ByteOrder.valueOf(orderName);

                            /* Check if the order is valid */
                            if(order == null) {
                                throw new IOException("order '" + orderName + "' is not recognized");
                            }
                        }

                        /* By default use no translation */
                        Translation translation = Translation.NONE;

                        if(transformerNode.containsChild("translation")) {

                            /* Get the name of the translation */
                            String translationName = transformerNode.getFirstChild("translation").getValue();

                            /* Get the translation from the name of the translation */
                            translation = Translation.valueOf(translationName);

                            /* Check if the translation is valid */
                            if(translation == null) {
                                throw new IOException("translation '" + translationName + "' is not recognized");
                            }
                        }

                        /* Create and add the block and transformer to the meta data */
                        NumericTransformer transformer = new NumericTransformer(translation, order);
                        data.addBlock(name, transformer);
                    } else {

                        /* Add the block to the meta data */
                        data.addBlock(name);
                    }
                }
            }

            /* Add the meta data to the meta data list */
            dataList.add(data);
        }
        
        return dataList;
    }
}
