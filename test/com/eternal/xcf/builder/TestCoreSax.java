package com.eternal.xcf.builder;

import com.eternal.stubs.CONTEXT_Simple;
import com.eternal.stubs.REQUEST_SimpleTest;
import com.eternal.xcf.core.XCFFacade;
import com.eternal.xcf.core.XCFLogger;
import com.eternal.xcf.sax.UTIL_Helper;

import junit.framework.TestCase;

public class TestCoreSax extends TestCase {
	
	public void testSimpleFile() throws Exception {
		XCFFacade facade = new XCFFacade();
		facade.getLogManager().setLogger(XCFLogger.LogTypes.DEBUG, facade.getLogManager().getLogger(XCFLogger.LOG_TO_CONSOLE));
		UTIL_Helper.interpretLocalFile(this, "simple.xml", facade, "facade");
		
		// create the request account.login(userName=myloginname, password=mypassword)
		REQUEST_SimpleTest request = new REQUEST_SimpleTest("account", "login");
		request.setContext(new CONTEXT_Simple());
		request.getContext().setFacade(facade);
		
		request.setParameter("user-name", "myloginname");
		request.setParameter("password", "mypassword");
		
		// process the request
		facade.process(request);
	}
}
