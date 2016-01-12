package problem.models.impl;

import java.io.OutputStream;

import problem.model.visitor.ModelVisitorAdapter;

public class ModelSDOutputStream extends ModelVisitorAdapter{
	
	OutputStream out;
	StringBuilder s;
	
	public ModelSDOutputStream(OutputStream out) {
		this.out = out;
	}

}
