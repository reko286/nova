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

package org.nova.util.script;

import javax.script.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hadyn Richard
 */
public final class ScriptEnvironment {

    /**
     * The script engine manager for all the script environments.
     */
    private static final ScriptEngineManager manager = new ScriptEngineManager();

    /**
     * The collection of scripts that have been compiled.
     */
    private Map<String, CompiledScript> scripts;

    /**
     * The script engine for this environment.
     */
    private ScriptEngine engine;

    /**
     * The script environment context.
     */
    private EnvironmentContext context;

    /**
     * Constructs a new {@link ScriptEnvironment};
     *
     * @param engineName    The name of the scripting engine to use.
     * @param context       The context of the environment.
     */
    public ScriptEnvironment(String engineName, EnvironmentContext context) {
        scripts = new HashMap<String, CompiledScript>();

        /* Fetch the scripting engine from the provided engine name */
        engine = manager.getEngineByName(engineName);
        if(engine == null) {
            throw new RuntimeException("no such scripting engine"); //TODO: Figure out a better exception for this.
        }

        /* Store the environment context in the global scope */
        engine.getBindings(ScriptContext.GLOBAL_SCOPE).put("ctx", context);

        this.context = context;
    }

    /**
     * Evalulates a script, the script must have been previously compiled.
     * 
     * @param name              The name of the script to evaluate.
     * @throws ScriptException  A script exception was thrown while evaluating the script.
     */
    public void eval(String name) throws ScriptException {
        
        /* Get and execute the compiled script */
        CompiledScript compiledScript = scripts.get(name);
        if(compiledScript == null) {
            throw new RuntimeException("script does not exist");
        }      
        compiledScript.eval();
    }

    /**
     * Loads a script.
     *
     * @param script    The script to load.
     * @throws ScriptException  A script exception was thrown while evaluating the script.
     */
    public void load(Script script) throws ScriptException {

        /* Compile the script from the source and execute it */
        Compilable compilable = (Compilable) engine;
        CompiledScript compiledScript = compilable.compile(script.getSource());

        /* Store the compiled script */
        scripts.put(script.getName(), compiledScript);
    }

    /**
     * Evaluates a script.
     *
     * @param script            The script to evaluate.
     * @throws ScriptException  A script exception was thrown while evaluating the script.
     */
    public void eval(Script script) throws ScriptException {
        
        /* Check if the script has already been pre-compiled */
        if(scripts.containsKey(script.getName())) {

            /* Evaluate the script */
            eval(script.getName());
        } else {

            /* Compile the script from the source and execute it */
            Compilable compilable = (Compilable) engine;
            CompiledScript compiledScript = compilable.compile(script.getSource());

            /* Store the compiled script and evaluate it */
            scripts.put(script.getName(), compiledScript);
            compiledScript.eval();
        }
    }
}
