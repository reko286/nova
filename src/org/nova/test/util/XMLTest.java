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

package org.nova.test.util;

import org.nova.util.xml.XMLNode;
import org.nova.util.xml.XMLParser;

import java.io.FileReader;

/**
 * Created by Hadyn Richard
 */
public final class XMLTest {

    /**
     * The main entry point of the test.
     *
     * @param args  The command line arguments.
     */
    public static void main(String[] args) {
        try {

            XMLParser parser = new XMLParser();

            /* Parse the XML file */
            XMLNode root =  parser.parse(new FileReader("./data/test/xml_test.xml"));

            /* Get the first node and commence testing */
            XMLNode first = root.getFirstChild("first");

            /* Assert that the first node has an inner child */
            assert(first.getFirstChild("inner") != null);

            /* Get the attributed node and check if it has its attribute value */
            XMLNode attributed = root.getFirstChild("attributed");

            /* Assert that the attributed node contains the specified attribute */
            assert(attributed.getAttribute("value") != null);

            /* Assert that the attributed node attribute value, 'value' is 'i_like_turtles' */
            assert(attributed.getAttribute("value").equals("i_like_turtles"));

        } catch(Exception ex) {
            System.out.println("Something went wrong: " + ex);
        }
    }
}
