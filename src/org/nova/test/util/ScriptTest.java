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

import org.nova.util.script.Script;
import org.nova.util.script.ScriptEnvironment;
import org.nova.util.script.context.GameEnvironmentContext;

import java.io.FileReader;

/**
 * Created by Hadyn Richard
 */
public final class ScriptTest {

    /** The main entry point of the program */
    public static void main(String[] args) {
        try {
            ScriptEnvironment environment = new ScriptEnvironment("jruby", new GameEnvironmentContext());

            /* Evaluate the test script */
            Script script = new Script("test_script", new FileReader("./data/test/test_script.rb"));
            environment.eval(script);

            /* Evaluate the test script again */
            environment.eval("test_script");

        } catch(Exception ex) {
            System.err.println("Something went wrong - " + ex);
        }
    }
}
