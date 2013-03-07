package com.eternal.scopeThree.request.parameter;

import com.eternal.xcf.core.XCFException;
import com.eternal.xcf.core.XCFRequest;
import com.eternal.xcf.request.parameter.XCFValidator;
import com.eternal.xcf.request.processor.XCFProcessingInstruction;

public class VALIDATOR_Required implements XCFValidator {

	public boolean validate(XCFRequest req,
			XCFProcessingInstruction parameterSpecification)
			throws XCFException {
		
		return false;
	}

}
