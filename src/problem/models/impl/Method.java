package problem.models.impl;

import problem.model.visitor.IModelVisitor;
import problem.models.api.IMethod;

public class Method implements IMethod {

	private String signature;
	private String access;
	private String name;

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Method(String signature, String access, String name) {
		this.signature = signature;
		this.access = access;
		this.name = name;
	}

	@Override
	public String getSignature() {
		return this.signature;
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
