package com.eternal.xcf.request.processor.instructions;

import java.util.ArrayList;

import com.eternal.xcf.core.XCFException;
import com.eternal.xcf.core.XCFRequest;
import com.eternal.xcf.request.processor.XCFProcessingInstruction;

public class INSTRUCTION_Operation extends INSTRUCTION_Composite {
	private ArrayList<XCFProcessingInstruction> alwaysExecuteInstructions = new ArrayList<XCFProcessingInstruction>();
	
	public INSTRUCTION_Operation(String name) {
		super(name);
	}

	@Override
	public boolean process(XCFRequest request) throws XCFException {
		boolean result = super.process(request);
		
		for (XCFProcessingInstruction instruction : alwaysExecuteInstructions) {
			if (instruction.process(request) == false) {
				result = false;	
			}
		}
		
		return result;
	}
	
	public void addInstruction(XCFProcessingInstruction instruction, boolean alwaysExecute) {
		if (!alwaysExecute) addInstruction(instruction);
		
		alwaysExecuteInstructions.add(instruction);
	}

}
