package com.eternal.xcf.core.test;

import com.eternal.stubs.MODULE_SimpleTest;
import com.eternal.stubs.REQUEST_SimpleTest;
import com.eternal.xcf.core.XCFFacade;

import junit.framework.TestCase;

public class TestSimpleFacade extends TestCase {
	private XCFFacade facade;

	protected void setUp() throws Exception {
		super.setUp();
		// Build the facade.  The facade will support 
		// requests to an account module.
		facade = new XCFFacade();
		MODULE_SimpleTest accountModule = new MODULE_SimpleTest();
		facade.putModule("account", accountModule);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testSimpleRequestProcess() throws Exception {
		// create the request account.login(userName=myloginname, password=mypassword)
		REQUEST_SimpleTest request = new REQUEST_SimpleTest("account", "login");
		request.setParameter("userName", "myloginname");
		request.setParameter("password", "mypassword");
		
		// process the request
		facade.process(request);
	}

}
