package com.eternal.stubs;

import java.util.Iterator;

import com.eternal.xcf.core.XCFException;
import com.eternal.xcf.core.XCFFacade;
import com.eternal.xcf.core.XCFModule;
import com.eternal.xcf.core.XCFRequest;

public class MODULE_SimpleTest implements XCFModule {

	String name;
	String defaultOperation;
	XCFFacade facade;

	public String getDefaultOperation() {
		return defaultOperation;
	}

	public String getName() {
		return name;
	}

	/**
	 * Process the request by logging out:
	 *   [module].[operation]([paramName1]=[paramValue1],...)
	 */
	public void process(XCFRequest req) throws XCFException {
		StringBuffer buf = new StringBuffer();
		buf.append("PROCESSING " + req.getModule() + "." + req.getOperation() + "(");
		Iterator<String> paramNames =  req.getParameterNames();
		
		
		String separator = "";
		while (paramNames.hasNext()) {
			String paramName = paramNames.next();
			buf.append(separator);
			buf.append(paramName + "=" + req.getParameter(paramName));
			separator=", ";
		}
		buf.append(")");
		facade.logInfo(buf.toString());

	}

	public void setDefaultOperation(String defaultOperation) {
		this.defaultOperation = defaultOperation;
	}

	public void setFacade(XCFFacade facade) {
		this.facade = facade;
	}

	public void setName(String name) {
		this.name = name;
	}

}
