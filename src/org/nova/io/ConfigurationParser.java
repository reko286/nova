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

import org.nova.Configuration;
import org.nova.ServerMode;
import org.nova.util.script.Script;
import org.nova.util.xml.XMLNode;
import org.nova.util.xml.XMLParser;
import org.xml.sax.SAXException;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Hadyn Richard
 */
public final class ConfigurationParser {

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
     * @param is    The input stream to parse the packet decoders from.
     */
    public ConfigurationParser(InputStream is) throws SAXException {
        this.is = is;

        parser = new XMLParser();
    }

    /**
     * Parses the configuration.
     *
     * @return  The parsed configuration.
     */
    public Map<ServerMode, Configuration> parse() throws IOException, SAXException {
        
        Map<ServerMode, Configuration> configurations = new HashMap<ServerMode, Configuration>();

        /* Parse the XML file */
        XMLNode rootNode = parser.parse(is);

        if(!rootNode.getName().equals("config")) {
            throw new IOException("root node name is invalid");
        }

        /* Add all the configurations to the configuration collection */
        List<XMLNode> modes = rootNode.getChildren();      
        for(XMLNode mode : modes) {

            /* Skip the global mode */
            if(mode.getName().equals("global")) {
                continue;
            }

            /* Get the server mode from the node name and put a new configuration value */
            ServerMode serverMode = ServerMode.valueOf(mode.getName().toUpperCase());
            if(serverMode == null) {
                throw new IOException("invalid server mode");
            }
            configurations.put(serverMode, new Configuration());
        }

        List<Configuration> targets = new LinkedList<Configuration>();
        for(XMLNode mode : modes) {

            /* Clear the targets */
            targets.clear();

            if(mode.getName().equals("global")) {

                /* Add each of the configurations to the targets list */
                for(Configuration configuration : configurations.values()) {
                    targets.add(configuration);
                }
            } else {

                /* By default add the configuration for that specific name */
                targets.add(configurations.get(ServerMode.valueOf(mode.getName().toUpperCase())));
            }

            /* Parse each of the attributes */
            for(XMLNode node : mode.getChildren()) {

                /* Parse a script to add to the targets */
                if(node.getName().equals("script")) {

                    /* Check if the script node is valid */
                    if(!node.containsChild("name") || !node.containsChild("source")) {
                        throw new IOException("invalid script node");
                    }

                    /* Get the parameter values */
                    String name = node.getFirstChild("name").getValue();
                    String source = node.getFirstChild("source").getValue();

                    /* Create and add the script to the target configurations */
                    Script script = new Script(name, new FileReader("./data/scripts/" + source));
                    for(Configuration target : targets) {
                        target.addScript(script);
                    }
                }

                if(node.getName().equals("worldid")) {
                    
                    /* Parse and set the target configuration world ids */
                    int worldId = Integer.parseInt(node.getValue());
                    for(Configuration target : targets) {
                        target.setWorldId(worldId);
                    }
                }

                if(node.getName().equals("baseport")) {

                    /* Parse and set the target configuration world ids */
                    int basePort = Integer.parseInt(node.getValue());
                    for(Configuration target : targets) {
                        target.setBasePort(basePort);
                    }
                }
            }
        }

        return configurations;
    }
}
