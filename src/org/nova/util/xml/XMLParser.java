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

package org.nova.util.xml;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Stack;

/**
 * Created by Hadyn Richard
 *
 * Notes:
 *
 *          This is reusable.
 */
public final class XMLParser extends DefaultHandler {

    /**
     * The XML reader.
     */
    private XMLReader reader;

    /**
     * The root node.
     */
    private XMLNode rootNode;

    /**
     * The current node that is being parsed.
     */
    private XMLNode currentNode;

    /**
     * The nodes currently being parsed.
     */
    private Stack<XMLNode> nodeStack;

    /**
     * Constructs a new {@link XMLParser};
     */
    public XMLParser() throws SAXException {
        reader = XMLReaderFactory.createXMLReader();
        nodeStack = new Stack<XMLNode>();
        initialize();
    }

    /**
     * Initializes the reader for this parser.
     */
    private void initialize() {
        reader.setContentHandler(this);
        reader.setDTDHandler(this);
        reader.setEntityResolver(this);
        reader.setErrorHandler(this);
    }
    
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        /* Always set the qualified name to lower case */
        qName = qName.toLowerCase();

        /* Check if the root node has been created yet */
        if(rootNode == null) {
            currentNode = rootNode = new XMLNode(qName);
        } else {
            
            /* Create the new node and push the current node to the stack before reassigning its value */
            XMLNode newNode = new XMLNode(qName);
            nodeStack.push(currentNode);
            currentNode = newNode;
        }

        /* Add all the attributes to the current node */
        for(int i = 0; i < attributes.getLength(); i++) {
            currentNode.addAttribute(attributes.getQName(i), attributes.getValue(i));
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

        /* Check if the current node is the root node */
        if(currentNode != rootNode) {

            /* Pop the previously pushed node off the stack and add the current node to the child list */
            XMLNode previousNode = nodeStack.pop();
            previousNode.addChild(currentNode);
            currentNode = previousNode;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        
        /* Set the value of the current node and trim all the newline and tab characters */
        currentNode.setValue(new String(ch, start, length).replaceAll("\n\t", "").trim());
    }


    /**
     * Parses the XML file with a reader.
     *
     * @param is    The input stream to use to parse the XML file.
     * @return      The root node.
     */
    public XMLNode parse(InputStream is) throws IOException, SAXException {
        return parse(new InputSource(is));
    }

    /**
     * Parses the XML file with a reader.
     *
     * @param reader    The reader to use to parse the XML file.
     * @return          The root node.
     */
    public XMLNode parse(Reader reader) throws IOException, SAXException {
        return parse(new InputSource(reader));
    }

    /**
     * Parses the XML file with an input source.
     *
     * @param source    The source of the XML file.
     * @return          The root node.
     */
    public XMLNode parse(InputSource source) throws IOException, SAXException {

        /* Reset the root node */
        rootNode = null;

        /* Parse the source */
        reader.parse(source);

        /* Check the root node */
        if(rootNode == null) {
            throw new IOException("root node is null");
        }

        return rootNode;
    }
}
