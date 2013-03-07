package com.eternal.xcf.core.response;

import com.eternal.stubs.CONTEXT_Simple;
import com.eternal.stubs.REQUEST_SimpleTest;
import com.eternal.xcf.core.XCFFacade;
import com.eternal.xcf.core.XCFLogger;
import com.eternal.xcf.sax.UTIL_Helper;

import junit.framework.TestCase;

public class TestResponse extends TestCase {
	
	public void testSimpleFile() throws Exception {
		XCFFacade facade = new XCFFacade();
		facade.getLogManager().setLogger(XCFLogger.LogTypes.DEBUG, facade.getLogManager().getLogger(XCFLogger.LOG_TO_CONSOLE));
		UTIL_Helper.interpretLocalFile(this, "simple.xml", facade, "facade");
		
		SERVICE_ResponseManager responseManager = (SERVICE_ResponseManager)facade.getService(SERVICE_ResponseManager.XCF_TAG);
		assertNotNull(responseManager);

		// create the request account.login(userName=myloginname, password=mypassword)
		REQUEST_SimpleTest request = new REQUEST_SimpleTest("account", "login");
		request.setContext(new CONTEXT_Simple());
		request.getContext().setFacade(facade);
		
		request.setParameter("user-name", "myloginname");
		request.setParameter("password", "mypassword");
		
		// process the request
		facade.process(request);
		
		// verify that the request resulted by setting "account.home" as
		// the response id
		String responseId = (String)request.getContext().getValue(XCFResponse.XCF_RESPONSE_ID);
		assertEquals("account.home", responseId);
		
		String[] nativeRequestInfo = new String[1];
		
		// now render an html response - the renderer should
		// write the name of a jsp file to the array we pass in
		com.eternal.xcf.core.response.UTIL_Helper.setResponseType(request, "html");
		XCFResponse response = responseManager.getResponse(request);
		response.render(request, nativeRequestInfo);
		assertEquals("/account/home.jsp", nativeRequestInfo[0]);

		// now render an nio response - the renderer should
		// write the name of a message to the array we pass in
		com.eternal.xcf.core.response.UTIL_Helper.setResponseType(request, "nio");
		response = responseManager.getResponse(request);
		response.render(request, nativeRequestInfo);
		assertEquals("account.display-home", nativeRequestInfo[0]);

		// create a new reqest, but this time don't set the password
		request = new REQUEST_SimpleTest("account", "login");
		request.setContext(new CONTEXT_Simple());
		request.getContext().setFacade(facade);
		
		request.setParameter("user-name", "myloginname");
		
		// process the request and verify that we got
		// the login failed response selected
		facade.process(request);
		responseId = (String)request.getContext().getValue(XCFResponse.XCF_RESPONSE_ID);
		assertEquals("account.login-failed", responseId);
		
		// now render an html response - the renderer should
		// write the name of a jsp file to the array we pass in
		com.eternal.xcf.core.response.UTIL_Helper.setResponseType(request, "html");
		response = responseManager.getResponse(request);
		response.render(request, nativeRequestInfo);
		assertEquals("/account/login.jsp", nativeRequestInfo[0]);

		// now render an nio response - the renderer should
		// write the name of a message to the array we pass in
		com.eternal.xcf.core.response.UTIL_Helper.setResponseType(request, "nio");
		response = responseManager.getResponse(request);
		response.render(request, nativeRequestInfo);
		assertEquals("account.handle-login-failed", nativeRequestInfo[0]);
	}
}
