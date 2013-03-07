package com.eternal.xcf.common.service;

import com.eternal.xcf.common.service.SERVICE_FlyweightFactory;
import com.eternal.xcf.core.XCFException;
import com.eternal.xcf.core.XCFFacade;
import com.eternal.xcf.core.XCFLogger;
import com.eternal.xcf.request.parameter.XCFSetter;
import com.eternal.xcf.request.parameter.XCFValidator;

import junit.framework.TestCase;

public class TestFlywieghtFactory extends TestCase {
	
	public void testFlywieghtFactoryScopePackageSearch() throws Exception {
		XCFFacade facade = new XCFFacade();
		SERVICE_FlyweightFactory factory = new SERVICE_FlyweightFactory();
		facade.getLogManager().setLogger(XCFLogger.LogTypes.DEBUG, facade.getLogManager().getLogger(XCFLogger.LOG_TO_CONSOLE));
		
		facade.putService(SERVICE_FlyweightFactory.XCF_TAG, factory);
		
		XCFValidator requiredScope1 = null; // exists in common package
		XCFValidator requiredScope2 = null; // same as scope 1
		XCFValidator requiredScope3 = null; // exists in scope 3 package
		
		XCFSetter customSetterScope1 = null; // exists in stub package
		XCFSetter customSetterScope2 = null; // exists in scope 2 package
		XCFSetter customSetterScope3 = null; // same as scope 2
		
		XCFValidator customValidatorScope3 = null; // exists in scope 3 package
		XCFSetter    setterWithLongNameScope3 = null; // exists in scope 3 package
				
		// add a rule that binds validator to .request.processors.VALIDATOR_
		factory.addConvention("validator", ".request.parameter.VALIDATOR_");
		
		// add a rule that binds setter to .request.processors.SETTER_
		factory.addConvention("setter", ".request.parameter.SETTER_");
		
		/////////////////////////////////////////////////////
		// SCOPE 1 TEST
		// push scope 1
		factory.pushScope();
		factory.addPackage("com.eternal.xcf.common");
		factory.addPackage("com.eternal.stubs");
		
		// ok, get the scope 1 flyweights
		requiredScope1 = (XCFValidator)factory.getInstance("validator", "required");
		customSetterScope1 = (XCFSetter)factory.getInstance("setter", "custom");
		
		// these next two shouldn't be there
		try {customValidatorScope3 = (XCFValidator)factory.getInstance("validator", "custom");} catch (XCFException e) {}
		try {setterWithLongNameScope3 = (XCFSetter)factory.getInstance("setter", "with-long-name");} catch (XCFException e) {}
		
		assertNotNull(requiredScope1);
		assertNotNull(customSetterScope1);
		
		assertTrue(requiredScope1 instanceof com.eternal.xcf.common.request.parameter.VALIDATOR_Required);
		assertTrue(customSetterScope1 instanceof com.eternal.stubs.request.parameter.SETTER_Custom);
			
		assertNull(customValidatorScope3);
		assertNull(setterWithLongNameScope3);
		
		/////////////////////////////////////////////////////
		// SCOPE 2 TEST
		// push scope 2
		factory.pushScope();
		factory.addPackage("com.eternal.scopeTwo");
		
		// ok, get the scope 2 flyweights
		requiredScope2 = (XCFValidator)factory.getInstance("validator", "required");
		customSetterScope2 = (XCFSetter)factory.getInstance("setter", "custom");
		
		// these next two shouldn't be there
		try {customValidatorScope3 = (XCFValidator)factory.getInstance("validator", "custom");} catch (XCFException e) {}
		try {setterWithLongNameScope3 = (XCFSetter)factory.getInstance("setter", "with-long-name");} catch (XCFException e) {}
		
		assertEquals(requiredScope1, requiredScope2);
		assertNotNull(customSetterScope2);
		assertFalse(customSetterScope1 == customSetterScope2);
		
		assertTrue(requiredScope2 instanceof com.eternal.xcf.common.request.parameter.VALIDATOR_Required);
		assertTrue(customSetterScope2 instanceof com.eternal.scopeTwo.request.parameter.SETTER_Custom);

		assertNull(customValidatorScope3);
		assertNull(setterWithLongNameScope3);
		
		/////////////////////////////////////////////////////
		// SCOPE 3 TEST
		// push scope 3
		factory.pushScope();
		factory.addPackage("com.eternal.scopeThree");
		
		// ok, get the scope 3 flyweights
		requiredScope3 = (XCFValidator)factory.getInstance("validator", "required");
		customSetterScope3 = (XCFSetter)factory.getInstance("setter", "custom");
		customValidatorScope3 = (XCFValidator)factory.getInstance("validator", "custom");
		setterWithLongNameScope3 = (XCFSetter)factory.getInstance("setter", "with-long-name");	
		
		assertNotNull(requiredScope3);
		assertFalse(requiredScope1 == requiredScope3);
		assertEquals(customSetterScope2, customSetterScope3);
		
		assertNotNull(customValidatorScope3);
		assertNotNull(setterWithLongNameScope3);
		
		assertTrue(requiredScope3 instanceof com.eternal.scopeThree.request.parameter.VALIDATOR_Required);
		assertTrue(customSetterScope3 instanceof com.eternal.scopeTwo.request.parameter.SETTER_Custom);
		assertTrue(customValidatorScope3 instanceof com.eternal.scopeThree.request.parameter.VALIDATOR_Custom);
		assertTrue(setterWithLongNameScope3 instanceof com.eternal.scopeThree.request.parameter.SETTER_WithLongName);
		
		/////////////////////////////////////////////////////
		// SCOPE 2 TEST after pop of Scope 3
		// pop scope 3
		factory.popScope();

		// ok, get the scope 2 flyweights
		requiredScope2 = (XCFValidator)factory.getInstance("validator", "required");
		customSetterScope2 = (XCFSetter)factory.getInstance("setter", "custom");
		
		// these next two shouldn't be there
		try {customValidatorScope3 = (XCFValidator)factory.getInstance("validator", "custom");} catch (XCFException e) {customValidatorScope3 = null;}
		try {setterWithLongNameScope3 = (XCFSetter)factory.getInstance("setter", "with-long-name");} catch (XCFException e) {setterWithLongNameScope3 = null;}
		
		assertEquals(requiredScope1, requiredScope2);
		assertNotNull(customSetterScope2);
		assertFalse(customSetterScope1 == customSetterScope2);
		
		assertTrue(requiredScope2 instanceof com.eternal.xcf.common.request.parameter.VALIDATOR_Required);
		assertTrue(customSetterScope2 instanceof com.eternal.scopeTwo.request.parameter.SETTER_Custom);

		assertNull(customValidatorScope3);
		assertNull(setterWithLongNameScope3);

		/////////////////////////////////////////////////////
		// SCOPE 3 TEST with a bogus path
		// push a new scope 3 with a non-existent package
		factory.pushScope();
		factory.addPackage("com.eternal.bogus");
		
		requiredScope3 = (XCFValidator)factory.getInstance("validator", "required");
		customSetterScope3 = (XCFSetter)factory.getInstance("setter", "custom");

		// these next two shouldn't be there
		try {customValidatorScope3 = (XCFValidator)factory.getInstance("validator", "custom");} catch (XCFException e) {customValidatorScope3 = null;}
		try {setterWithLongNameScope3 = (XCFSetter)factory.getInstance("setter", "with-long-name");} catch (XCFException e) {setterWithLongNameScope3 = null;}

		assertEquals(requiredScope1, requiredScope3);
		assertEquals(customSetterScope2, customSetterScope3);
		
		assertTrue(requiredScope3 instanceof com.eternal.xcf.common.request.parameter.VALIDATOR_Required);
		assertTrue(customSetterScope3 instanceof com.eternal.scopeTwo.request.parameter.SETTER_Custom);

		assertNull(customValidatorScope3);
		assertNull(setterWithLongNameScope3);
		
		/////////////////////////////////////////////////////
		// SCOPE 2 TEST after pop of Scope 3
		// pop scope 3
		factory.popScope();
		// ok, get the scope 2 flyweights
		requiredScope2 = (XCFValidator)factory.getInstance("validator", "required");
		customSetterScope2 = (XCFSetter)factory.getInstance("setter", "custom");
		
		// these next two shouldn't be there
		try {customValidatorScope3 = (XCFValidator)factory.getInstance("validator", "custom");} catch (XCFException e) {customValidatorScope3 = null;}
		try {setterWithLongNameScope3 = (XCFSetter)factory.getInstance("setter", "with-long-name");} catch (XCFException e) {setterWithLongNameScope3 = null;}
		
		assertEquals(requiredScope1, requiredScope2);
		assertNotNull(customSetterScope2);
		assertFalse(customSetterScope1 == customSetterScope2);
		
		assertTrue(requiredScope2 instanceof com.eternal.xcf.common.request.parameter.VALIDATOR_Required);
		assertTrue(customSetterScope2 instanceof com.eternal.scopeTwo.request.parameter.SETTER_Custom);

		assertNull(customValidatorScope3);
		assertNull(setterWithLongNameScope3);
		
		/////////////////////////////////////////////////////
		// SCOPE 3 TEST
		// push scope 3 with old package
		factory.pushScope();
		factory.addPackage("com.eternal.scopeThree");
		
		// ok, get the scope 3 flyweights
		requiredScope3 = (XCFValidator)factory.getInstance("validator", "required");
		customSetterScope3 = (XCFSetter)factory.getInstance("setter", "custom");
		customValidatorScope3 = (XCFValidator)factory.getInstance("validator", "custom");
		setterWithLongNameScope3 = (XCFSetter)factory.getInstance("setter", "with-long-name");	
		
		assertNotNull(requiredScope3);
		assertFalse(requiredScope1 == requiredScope3);
		assertEquals(customSetterScope2, customSetterScope3);
		
		assertNotNull(customValidatorScope3);
		assertNotNull(customValidatorScope3);
				
		assertTrue(requiredScope3 instanceof com.eternal.scopeThree.request.parameter.VALIDATOR_Required);
		assertTrue(customSetterScope3 instanceof com.eternal.scopeTwo.request.parameter.SETTER_Custom);
		assertTrue(customValidatorScope3 instanceof com.eternal.scopeThree.request.parameter.VALIDATOR_Custom);
		assertTrue(setterWithLongNameScope3 instanceof com.eternal.scopeThree.request.parameter.SETTER_WithLongName);

		/////////////////////////////////////////////////////
		// SCOPE 2 TEST after pop of Scope 3
		// pop scope 3
		factory.popScope();
		requiredScope2 = (XCFValidator)factory.getInstance("validator", "required");
		customSetterScope2 = (XCFSetter)factory.getInstance("setter", "custom");
		
		// these next two shouldn't be there
		try {customValidatorScope3 = (XCFValidator)factory.getInstance("validator", "custom");} catch (XCFException e) {customValidatorScope3 = null;}
		try {setterWithLongNameScope3 = (XCFSetter)factory.getInstance("setter", "with-long-name");} catch (XCFException e) {setterWithLongNameScope3 = null;}
		
		assertEquals(requiredScope1, requiredScope2);
		assertNotNull(customSetterScope2);
		assertFalse(customSetterScope1 == customSetterScope2);
		
		assertTrue(requiredScope2 instanceof com.eternal.xcf.common.request.parameter.VALIDATOR_Required);
		assertTrue(customSetterScope2 instanceof com.eternal.scopeTwo.request.parameter.SETTER_Custom);

		assertNull(customValidatorScope3);
		assertNull(setterWithLongNameScope3);

		/////////////////////////////////////////////////////
		// SCOPE 1 TEST after pop of Scope 2
		// pop scope 2
		factory.popScope();
		// ok, get the scope 1 flyweights
		requiredScope1 = (XCFValidator)factory.getInstance("validator", "required");
		customSetterScope1 = (XCFSetter)factory.getInstance("setter", "custom");
		
		// these next two shouldn't be there
		try {customValidatorScope3 = (XCFValidator)factory.getInstance("validator", "custom");} catch (XCFException e) {customValidatorScope3 = null;}
		try {setterWithLongNameScope3 = (XCFSetter)factory.getInstance("setter", "with-long-name");} catch (XCFException e) {setterWithLongNameScope3 = null;}
		
		assertNotNull(requiredScope1);
		assertNotNull(customSetterScope1);
		
		assertTrue(requiredScope1 instanceof com.eternal.xcf.common.request.parameter.VALIDATOR_Required);
		assertTrue(customSetterScope1 instanceof com.eternal.stubs.request.parameter.SETTER_Custom);

		assertNull(customValidatorScope3);
		assertNull(setterWithLongNameScope3);

		/////////////////////////////////////////////////////
		// NULL SCOPE TEST 
		// pop scope 1
		factory.popScope();
		try {requiredScope1 = (XCFValidator)factory.getInstance("validator", "custom");} catch (XCFException e) {requiredScope1 = null;}
		assertNull(requiredScope1);
	}
	
	public void testFlyweightFactoryScopeConventionSearch() throws Exception {
		XCFFacade facade = new XCFFacade();
		SERVICE_FlyweightFactory factory = new SERVICE_FlyweightFactory();
		facade.getLogManager().setLogger(XCFLogger.LogTypes.DEBUG, facade.getLogManager().getLogger(XCFLogger.LOG_TO_CONSOLE));
		
		XCFValidator requiredScope1 = null; // exists in common package
		XCFValidator requiredScope2 = null; // same as scope 1
		
		XCFSetter customSetterScope1 = null; // doesn't exist which starting convention
		XCFSetter customSetterScope2 = null; // exists in scope 2 convention
		
		facade.putService(SERVICE_FlyweightFactory.XCF_TAG, factory);
		
		/////////////////////////////////////////////////////
		// START out with there being a convention for 
		// validator, but not setter -- we do this by setting 
		// a bogus convention for setter
		factory.addConvention("setter", ".bogus.convention.SETTER_");
		
		
		// ok, get the scope 1 flyweights
		requiredScope1 = (XCFValidator)factory.getInstance("validator", "required");
		try {customSetterScope1 = (XCFSetter)factory.getInstance("setter", "custom");} catch (XCFException e) {}
		
		assertNotNull(requiredScope1);
		assertNull(customSetterScope1);
		
		assertTrue(requiredScope1 instanceof com.eternal.xcf.common.request.parameter.VALIDATOR_Required);
		
		/////////////////////////////////////////////////////
		// Next, push a new scope and fix the setter convention
		factory.pushScope();
		factory.addPackage("com.eternal.stubs");
		factory.addConvention("setter", ".request.parameter.SETTER_");
		
		// ok, get the scope 2 flyweights
		requiredScope2 = (XCFValidator)factory.getInstance("validator", "required");
		customSetterScope2 = (XCFSetter)factory.getInstance("setter", "custom");
		
		assertNotNull(requiredScope2);
		assertNotNull(customSetterScope2);
		
		assertEquals(requiredScope1, requiredScope2);
		
		assertTrue(requiredScope2 instanceof com.eternal.xcf.common.request.parameter.VALIDATOR_Required);
		assertTrue(customSetterScope2 instanceof com.eternal.stubs.request.parameter.SETTER_Custom);

		/////////////////////////////////////////////////////
		// Now pop the scope and verify we are back where
		// we started.
		factory.popScope();
		
		requiredScope1 = (XCFValidator)factory.getInstance("validator", "required");
		try {customSetterScope1 = (XCFSetter)factory.getInstance("setter", "custom");} catch (XCFException e) {}
		
		assertNotNull(requiredScope1);
		assertNull(customSetterScope1);
		
		assertTrue(requiredScope1 instanceof com.eternal.xcf.common.request.parameter.VALIDATOR_Required);
	}
	
	public void testFlyweightFactorySearchPath() throws Exception {
		XCFFacade facade = new XCFFacade();
		SERVICE_FlyweightFactory factory = new SERVICE_FlyweightFactory();
		facade.getLogManager().setLogger(XCFLogger.LogTypes.DEBUG, facade.getLogManager().getLogger(XCFLogger.LOG_TO_CONSOLE));
		
		facade.putService(SERVICE_FlyweightFactory.XCF_TAG, factory);
		
		// add a rule that binds validator to .request.processors.VALIDATOR_
		factory.addConvention("validator", ".request.parameter.VALIDATOR_");

		XCFValidator requiredScope1 = null; // exists in common package
		
		factory.pushScope();
		factory.addPackage("com.eternal.xcf.common");
		factory.addPackage("com.eternal.scopeThree");
		requiredScope1 = (XCFValidator)factory.getInstance("validator", "required");
		
		assertTrue(requiredScope1 instanceof com.eternal.scopeThree.request.parameter.VALIDATOR_Required);
		
	}

}
