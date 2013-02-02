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

import org.nova.util.meta.PacketData;
import org.nova.net.packet.PacketVariable.VariableType;
import org.nova.net.packet.PacketVariable;
import org.nova.util.xml.XMLNode;
import org.nova.util.xml.XMLParser;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Hadyn Richard
 */
public final class PacketParser {

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
     * @param is    The input stream to decode the packet meta data from.
     */
    public PacketParser(InputStream is) throws SAXException {
        this.is = is;

        parser = new XMLParser();
    }

    /**
     * Parses the packet meta data from the XML file.
     *
     * @return  The result packet meta data map.
     */
    public Map<String, PacketData> parse() throws IOException, SAXException {
        
        Map<String, PacketData> data = new HashMap<String, PacketData>();

        /* Parse the XML file */
        XMLNode rootNode = parser.parse(is);

        /* Check to make sure that the root node is correctly named */
        if(!rootNode.getName().equals("packets")) {
            throw new IOException("root node name is invalid");
        }
        
        /* Get each of the packet declarations */
        List<XMLNode> packetNodes = rootNode.getChildren("packet");
        
        for(XMLNode node : packetNodes) {

            /* Check if the name node exists */
            if(!node.containsChild("name")) {
                throw new IOException("name not declared");
            }

            /* Get the value of the name node and check for collisions */
            String name = node.getFirstChild("name").getValue();
            if(data.containsKey(name)) {
                throw new IOException("packet with name '" + name + "' already exists");
            }

            /* Check if the size node exists */
            if(!node.containsChild("size")) {
                throw new IOException("size not declared");
            }

            String packetSize = node.getFirstChild("size").getValue();
            
            /* Parse the actual size of the packet from the size value */
            int actualSize = -1;
            if(packetSize.equals("VAR_BYTE")) {
                actualSize = PacketData.VAR_BYTE;
            } else if(packetSize.equals("VAR_SHORT")) {
                actualSize = PacketData.VAR_SHORT;
            } else {
                actualSize = Integer.parseInt(packetSize);
            }

            PacketData metaData = new PacketData(name, actualSize);

            /* Check if the variables node exists */
            if(!node.containsChild("variables")) {
                throw new IOException("variables not declared");
            }

            /* Get the variables node */
            XMLNode variablesNode = node.getFirstChild("variables");

            /* Get the variable nodes */
            List<XMLNode> variableNodes =  variablesNode.getChildren("variable");

            for(XMLNode variableNode : variableNodes) {

                /* Check if the variable is valid */
                if(!variableNode.containsChild("name") || !variableNode.containsChild("type")) {
                    throw new IOException("invalid variable");
                }
                
                String variableName = variableNode.getFirstChild("name").getValue();
                String typeName = variableNode.getFirstChild("type").getValue();

                /* Get the variable type for its name */
                VariableType variableType = VariableType.valueOf(typeName);

                /* Check if the variable name is valid */
                if(variableName == null) {
                    throw new IOException("variable type '" + variableName + "' is not valid");
                }

                /* Create the packet variable */
                PacketVariable packetVariable = new PacketVariable(variableName, variableType);

                /* Add the packet variable */
                metaData.addVariable(packetVariable);
            }

            /* Add the meta data to the map */
            data.put(name, metaData);
        }

        return data;
    }
}
