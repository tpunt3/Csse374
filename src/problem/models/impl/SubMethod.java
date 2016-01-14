package problem.models.impl;

import problem.model.visitor.IModelVisitor;
import problem.models.api.IClass;
import problem.models.api.ISubMethod;

public class SubMethod implements ISubMethod {
	private String clazzName;
	private String name;
	private String args;
	private String qualifiedClassName;

	public SubMethod(String qualifiedClassName, String clazzName, String name, String args) {
		this.qualifiedClassName = qualifiedClassName;
		this.clazzName = clazzName;
		this.name = name;
		this.args = args;
	}
	
	public String getQualifiedClassName() {
		return qualifiedClassName;
	}

	public String getClazzName() {
		return clazzName;
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

	@Override
	public void writeFile(IModelVisitor sdWriter) {
		// TODO Auto-generated method stub
		
	}

}
