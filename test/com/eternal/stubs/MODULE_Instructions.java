package com.eternal.stubs;

import java.util.HashMap;

import com.eternal.xcf.core.XCFException;
import com.eternal.xcf.core.XCFFacade;
import com.eternal.xcf.core.XCFModule;
import com.eternal.xcf.core.XCFRequest;
import com.eternal.xcf.request.processor.XCFProcessingInstruction;

public class MODULE_Instructions implements XCFModule {

	String name;
	String defaultOperation;
	XCFFacade facade;
	HashMap<String, XCFProcessingInstruction> operations = new HashMap<String, XCFProcessingInstruction>();
	
	public void addOperationHandler(String operation, XCFProcessingInstruction handler) {
		operations.put(operation, handler);
	}

	public String getDefaultOperation() {
		return defaultOperation;
	}

	public String getName() {
		return name;
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

	/**
	 * Process the request looking up the operation handler and invoking
	 * the process method on that handler.
	 */
	public void process(XCFRequest req) throws XCFException {
		XCFProcessingInstruction handler = operations.get(req.getOperation());
		handler.process(req);

	}
}
