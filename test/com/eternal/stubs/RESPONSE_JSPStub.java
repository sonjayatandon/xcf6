package com.eternal.stubs;

import java.util.HashMap;

import com.eternal.xcf.core.XCFException;
import com.eternal.xcf.core.XCFFacade;
import com.eternal.xcf.core.XCFRequest;
import com.eternal.xcf.core.response.XCFResponse;

public class RESPONSE_JSPStub implements XCFResponse {
	String name;
	HashMap<String, Object> properties = new HashMap<String, Object>();

	public String getName() {
		return name;
	}

	public Object getProperty(String propertyName) {
		return properties.get(propertyName);
	}

	public void render(XCFRequest req, Object[] nativeRequestInfo)
			throws XCFException {
		XCFFacade facade = req.getContext().getFacade();
		facade.logInfo("Rendering JSP:" + getProperty("jsp"));
		nativeRequestInfo[0] = getProperty("jsp");
	}

	public void setName(String responseId) {
		this.name = responseId;
	}

	public void setProperty(String propertyName, Object value) {
		properties.put(propertyName, value);
	}

}
