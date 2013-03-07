package com.eternal.xcf.request.test;

import com.eternal.stubs.CMD_PrintRequest;
import com.eternal.stubs.CONTEXT_Simple;
import com.eternal.stubs.MODULE_Instructions;
import com.eternal.stubs.REQUEST_SimpleTest;
import com.eternal.xcf.common.request.parameter.VALIDATOR_Required;
import com.eternal.xcf.core.XCFFacade;
import com.eternal.xcf.request.parameter.XCFValidator;
import com.eternal.xcf.request.processor.XCFProcessingInstruction;
import com.eternal.xcf.request.processor.instructions.INSTRUCTION_Composite;
import com.eternal.xcf.request.processor.instructions.INSTRUCTION_ProcessParameter;

import junit.framework.TestCase;

public class TestSimpleFacade extends TestCase {
	private XCFFacade facade;

	protected void setUp() throws Exception {
		super.setUp();
		// Build the facade.  The facade will support 
		// requests to an account module.
		facade = new XCFFacade();
		MODULE_Instructions accountModule = new MODULE_Instructions();
		
		// create the required flyweight
		XCFValidator required = new VALIDATOR_Required();
		
		//////////////////////////////////////////////////////////////////////////////////////////
		// create the request handler for the "login" operation using the following specification
		//   operation name: login
		//     parameter 1: userName
		//      validation: required
		//     parameter 2: password
		//      validation: required
		//   execute: CMD_PrintRequest
		INSTRUCTION_Composite handler = new INSTRUCTION_Composite("login");
		
		// create the "userName" parameter processor
		INSTRUCTION_ProcessParameter paramSpecification = new INSTRUCTION_ProcessParameter("userName");
		paramSpecification.addValidator(required);
		handler.addInstruction(paramSpecification);
		
		// create the "password" parameter processor
		paramSpecification = new INSTRUCTION_ProcessParameter("password");
		paramSpecification.addValidator(required);
		handler.addInstruction(paramSpecification);
		
		// create the command we will execute
		XCFProcessingInstruction command = new CMD_PrintRequest();
		handler.addInstruction(command);
		
		// add the operation handler to the module
		accountModule.addOperationHandler("login", handler);
		//////////////////////////////////////////////////////////////////////////////////////////

		// add the account module to the facade
		facade.putModule("account", accountModule);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testSimpleRequestProcess() throws Exception {
		// create the request account.login(userName=myloginname, password=mypassword)
		REQUEST_SimpleTest request = new REQUEST_SimpleTest("account", "login");
		request.setContext(new CONTEXT_Simple());
		request.getContext().setFacade(facade);
		
		request.setParameter("userName", "myloginname");
		request.setParameter("password", "mypassword");
		
		// process the request
		facade.process(request);
	}
}
