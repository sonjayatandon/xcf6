package com.eternal.saxTest.builder;

import org.xml.sax.Attributes;

import com.eternal.xcf.sax.SAXEL_Composite;

public class SAXEL_SecondTag extends SAXEL_Composite {
	
	public SAXEL_SecondTag() {
		super("second-tag");
	}

	@Override
	public void startInterpreting(String uri, String name, String rawName, Attributes attributes) {
		super.startInterpreting(uri, name, rawName, attributes);
		
		getHandler().getFacade().logInfo("In second-tag");
	}
}
