package com.eternal.stubs;

import java.util.HashMap;

import com.eternal.xcf.core.XCFContext;
import com.eternal.xcf.core.XCFException;
import com.eternal.xcf.core.XCFFacade;

public class CONTEXT_Simple implements XCFContext {
	XCFFacade facade;
	HashMap<String, Object> context = new HashMap<String, Object>();

	// for the purposes of this example, we only need
	// a get/set facade.
	public void setFacade(XCFFacade facade) {
		this.facade = facade;
	}

	public XCFFacade getFacade() throws XCFException {
		return facade;
	}

	public Object getValue(String address) throws XCFException {
		
		return context.get(address);
	}

	public void putValue(String address, Object value) throws XCFException {
		context.put(address, value);
	}
}
