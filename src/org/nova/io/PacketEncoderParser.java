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

import org.nova.net.packet.transformers.NumericTransformer;
import org.nova.util.meta.PacketEncoderData;
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
public final class PacketEncoderParser extends PacketCodecParser {

    /**
     * Constructs a new {@link PacketEncoderParser};
     *
     * @param is    The input stream to parse the packet encoders from.
     */
    public PacketEncoderParser(InputStream is) throws SAXException {
        super(is);
    }

    /**
     * Parses the packet encoder data from the XML file.
     *
     * @return  The list of packet encoder data.
     */
    public List<PacketEncoderData> parse() throws IOException, SAXException {

        List<PacketEncoderData> dataList = new LinkedList<PacketEncoderData>();

        /* Parse the XML file */
        XMLNode rootNode = parser.parse(is);

        /* Check to make sure that the root node is correctly named */
        if(!rootNode.getName().equals("encoders")) {
            throw new IOException("root node name is invalid");
        }

        /* Get each of the decoder declarations */
        List<XMLNode> decoderNodes = rootNode.getChildren("encoder");

        for(XMLNode node : decoderNodes) {

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
            PacketEncoderData data = new PacketEncoderData(id, packetName);

            /* Parse the block data */
            parseBlockData(node, data);

            /* Add the meta data to the meta data list */
            dataList.add(data);
        }

        return dataList;
    }
}