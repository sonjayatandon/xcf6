package com.eternal.saxTest.builder;

import org.xml.sax.Attributes;

import com.eternal.xcf.sax.SAXEL_Composite;

public class SAXEL_FirstTag extends SAXEL_Composite {
	
	public SAXEL_FirstTag() {
		super("first-tag");
	}

	@Override
	public void startInterpreting(String uri, String name, String rawName, Attributes attributes) {
		super.startInterpreting(uri, name, rawName, attributes);
		
		getHandler().getFacade().logInfo("In first-tag");
	}
}
