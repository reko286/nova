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

import org.nova.io.ConfigurationParser;
import org.nova.net.MessageHandler;
import org.nova.net.PacketHandler;
import org.nova.util.script.Script;
import org.nova.util.script.ScriptEnvironment;
import org.nova.util.script.context.GameEnvironmentContext;
import org.xml.sax.SAXException;

import javax.script.ScriptException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by Hadyn Richard
 */
public final class Server {

    /**
     * The logger for this class.
     */
    private static final Logger logger = Logger.getLogger(Server.class.getName());

    /**
     * The command line arguments.
     * 
     * @param args  The command line arguments.
     */
    public static void main(String[] args) throws Exception { // TODO: Better exception handling

        /* Check if the arguments are correct */
        if(args.length < 1) {
            System.err.println("Usage: mode");
            System.exit(0);
        }
        
        System.err.println("`7MN.   `7MF'                                                              \n" +
                           "  MMN.    M                                                                \n" +
                           "  M YMb   M  ,pW\"Wq.`7M'   `MF',6\"Yb.  Created by Hadyn Richard aka Sini \n" +
                           "  M  `MN. M 6W'   `Wb VA   ,V 8)   MM  Thanks to: Trey                     \n" +
                           "  M   `MM.M 8M     M8  VA ,V   ,pm9MM                                      \n" +
                           "  M     YMM YA.   ,A9   VVV   8M   MM                                      \n" +
                           ".JML.    YM  `Ybmd9'     W    `Moo9^Yo.                                    \n" +
                           "-----------------------------------------------------------------------------");
    
        logger.info("Starting up...");

        /* Get the server mode from the arguments */
        ServerMode serverMode = ServerMode.valueOf(args[0].toUpperCase());
        if(serverMode == null) {
            throw new RuntimeException("invalid server mode");
        }
        
        Server server = new Server();
        server.init(serverMode);

        logger.info("Finished loading!");
    }

    /**
     * The server configuration.
     */
    private Configuration configuration;

    /**
     * Constructs a new {@link Server};
     */
    private Server() {}

    /**
     * Initializes the server.
     *
     * @param mode  The server mode to initialize with.
     */
    private void init(ServerMode mode) throws IOException, SAXException, ScriptException {

        logger.info("Initializing the server...");

        /* Parse the server configuration */
        logger.info("Parsing the server configurations...");
        ConfigurationParser configurationParser = new ConfigurationParser(new FileInputStream("./data/config.xml"));
        Map<ServerMode, Configuration> configurations = configurationParser.parse();

        /* Get the configuration for the mode and check if it is valid */
        configuration = configurations.get(mode);
        if(configuration == null) {
            throw new RuntimeException("EEK! no such configuration for server mode");
        }

        GameEnvironmentContext environmentContext = new GameEnvironmentContext();
        ScriptEnvironment scriptEnvironment = new ScriptEnvironment("jruby", environmentContext);

        MessageHandler handler = new MessageHandler();
        environmentContext.setMessageHandler(handler);

        logger.info("Evaluating each of the startup scripts...");
        for(Script script : configuration.getScripts()) {
            scriptEnvironment.eval(script);
        }
    }
}
