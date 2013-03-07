package com.eternal.stubs;

import java.util.Iterator;

import com.eternal.xcf.core.XCFException;
import com.eternal.xcf.core.XCFFacade;
import com.eternal.xcf.core.XCFRequest;
import com.eternal.xcf.request.processor.instructions.INSTRUCTION_Command;

public class CMD_PrintRequest extends INSTRUCTION_Command {

	/**
	 * Execute the command by logging out:
	 *   [module].[operation]([paramName1]=[paramValue1],...)
	 */
	public void execute(XCFRequest request) throws XCFException {
		XCFFacade facade = request.getContext().getFacade();
		
		StringBuffer buf = new StringBuffer();
		buf.append("PROCESSING " + request.getModule() + "." + request.getOperation() + "(");
		Iterator<String> paramNames =  request.getParameterNames();
		
		String separator = "";
		while (paramNames.hasNext()) {
			String paramName = paramNames.next();
			buf.append(separator);
			buf.append(paramName + "=" + request.getParameter(paramName));
			separator=", ";
		}
		buf.append(")");
		facade.logInfo(buf.toString());

	}
}
