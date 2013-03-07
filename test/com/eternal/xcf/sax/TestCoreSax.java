package com.eternal.xcf.sax;

import com.eternal.xcf.core.XCFFacade;

import junit.framework.TestCase;

public class TestCoreSax extends TestCase {
	
	public void testSimpleFile() throws Exception {
		XCFFacade facade = new XCFFacade();
		UTIL_Helper.interpretLocalFile(this, "simple.xml", facade, "facade");
	}
}
