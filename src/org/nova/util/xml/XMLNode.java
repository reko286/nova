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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Hadyn Richard
 */
public final class XMLNode {

    /**
     * The name of this node.
     */
    private String name;

    /**
     * The value of this node.
     */
    private String value;

    /**
     * The children nodes.
     */
    private List<XMLNode> children;

    /**
     * The attributes of this node.
     */
    private Map<String, String> attributes;

    /**
     * Constructs a new {@link XMLNode};
     */
    public XMLNode(String name) {
        this.name = name;
        
        children = new LinkedList<XMLNode>();
        attributes = new HashMap<String, String>();
    }

    /**
     * Gets the name of this node.
     *
     * @return  The name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of this node.
     *
     * @param value The string value.
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of this node.
     *
     * @return  The value.
     */
    public String getValue() {
        return value;
    }

    /**
     * Adds a node to the children node list.
     *
     * @param node  The node to add.
     */
    public void addChild(XMLNode node) {
        children.add(node);
    }

    /**
     * Checks if a child with the specified name is contained within this node.
     *
     * @param name  The name of the child to find.
     * @return      If a child node with the specified name exists.
     */
    public boolean containsChild(String name) {

        for(XMLNode child : children) {

            /* Check if the names are equal and return true if it is */
            if(child.name.equals(name)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Gets the first child with the specified name.
     *
     * @param name  The name of the node to find.
     * @return      The node.
     */
    public XMLNode getFirstChild(String name) {
        for(XMLNode child : children) {

            /* Check if the names are equal and return it if it is */
            if(child.name.equals(name)) {
                return child;
            }
        }

        return null;
    }

    /**
     * Gets the list of children XML nodes by name.
     *
     * @param name  The name of the nodes to find.
     * @return      The list of nodes.
     */
    public List<XMLNode> getChildren(String name) {
        List<XMLNode> nodes = new LinkedList<XMLNode>();
        for(XMLNode child : children) {
            
            /* Check if the names are equal and add it to the list if they are */
            if(child.name.equals(name)) {
                nodes.add(child);
            }
        }

        return nodes;
    }

    /**
     * Gets the children for this node.
     *
     * @return  The child nodes.
     */
    public List<XMLNode> getChildren() {
        return children;
    }

    /**
     * Adds an attribute to the attributes map.
     *
     * @param name  The name of the attribute.
     * @param value The value of the attribute.
     */
    public void addAttribute(String name, String value) {
        attributes.put(name, value);
    }

    /**
     * Gets an attribute for this node.
     *
     * @param name  The name of the attribute to get.
     * @return      The attribute value.
     */
    public String getAttribute(String name) {
        return attributes.get(name);
    }
}
