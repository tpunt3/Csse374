package problem.models.impl;

import problem.model.visitor.IModelVisitor;
import problem.models.api.IMethod;

public class Method implements IMethod {

	private String access;
	private String name;
	private String args;
	private String returnType;
	
	public String getReturnType(){
		return returnType;
	}

	public String getArgs() {
		return args;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Method(String access, String name, String args, String returnType) {
		this.access = access;
		this.name = name;
		this.args = args;
		this.returnType = returnType;
	}

	@Override
	public String getAccess() {
		return this.access;
	}

	@Override
	public void accept(IModelVisitor v) {
		v.preVisit(this);
		v.visit(this);
		v.postVisit(this);
	}

}
