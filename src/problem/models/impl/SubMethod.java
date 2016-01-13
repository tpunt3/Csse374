package problem.models.impl;

import problem.model.visitor.IModelVisitor;
import problem.models.api.ISubMethod;

public class SubMethod implements ISubMethod {
	private String clazz;
	private String name;
	private String args;

	public SubMethod(String clazz, String name, String args) {
		this.clazz = clazz;
		this.name = name;
		this.args = args;
	}
	
	public String getClazzName() {
		return clazz;
	}
	
	public String getMethodName() {
		return name;
	}
	
	public String getArgs() {
		return args;
	}
	
	@Override
	public void accept(IModelVisitor v) {
		// TODO Auto-generated method stub

	}

	@Override
	public void acceptSequence(IModelVisitor v, ISubMethod sm, int depth) {
		// TODO Auto-generated method stub
		
	}

}
