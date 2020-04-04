package com.hellonms.platforms.emp_util.dynamic_code;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import com.hellonms.platforms.emp_core.share.error.ERROR_CODE_CORE;
import com.hellonms.platforms.emp_core.share.error.EmpException;

public class UtilJavaScript {

	public static ScriptEngine evalScript(String script) throws EmpException {
		try {
			ScriptEngineManager factory = new ScriptEngineManager();
			ScriptEngine engine = factory.getEngineByName("JavaScript");
			engine.eval(script);
			return engine;
		} catch (Exception e) {
			throw new EmpException(e, ERROR_CODE_CORE.FILE_IO);
		}
	}

	public static void invokeMethod(ScriptEngine engine, String obj_name, String method_name, Object... args) throws EmpException {
		try {
			Invocable inv = (Invocable) engine;
			Object obj = engine.get(obj_name);
			inv.invokeMethod(obj, method_name, args);
		} catch (Exception e) {
			throw new EmpException(e, ERROR_CODE_CORE.FILE_IO);
		}
	}

}
