package com.eternal.stubs;

import java.util.HashMap;
import java.util.Iterator;

import com.eternal.xcf.core.XCFContext;
import com.eternal.xcf.core.XCFRequest;

public class REQUEST_SimpleTest implements XCFRequest {
	private String module;
	private String operation;
	private XCFContext context;
	private HashMap<String, String> parameters = new HashMap<String, String>();

	public REQUEST_SimpleTest(String module, String operation) {
		this.module = module;
		this.operation = operation;
	}
	
	public XCFContext getContext() {
		return context;
	}

	public String getModule() {
		return module;
	}

	public Object getNativeListener() {
		return null;
	}

	public Object getNativeRequest() {
		return null;
	}

	public Object getNativeResponse() {
		return null;
	}

	public String getOperation() {
		return operation;
	}

	public String getParameter(String paramName) {
		return parameters.get(paramName);
	}

	public Iterator<String> getParameterNames() {
		return parameters.keySet().iterator();
	}

	public void setContext(XCFContext context) {
		this.context = context;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public void setNativeListener(Object listener) {
	}

	public void setNativeRequest(Object req) {
	}

	public void setNativeResponse(Object resp) {
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public void setParameter(String paramName, String paramValue) {
		parameters.put(paramName, paramValue);
	}

	public void addNotice(String message) {
	}

	public void clearNotice() {
	}

	public Iterator<String> getNotices() {
		return null;
	}

	public boolean parameterExists(String paramName) {
		String parameterValue = getParameter(paramName);
		return parameterValue != null;
	}

}
