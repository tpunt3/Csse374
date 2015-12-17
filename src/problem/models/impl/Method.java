package problem.models.impl;

import problem.car.visitor.IVisitor;
import problem.models.api.IMethod;

public class Method implements IMethod {

	private String signature;
	private String access;

	public Method(String signature, String access) {
		this.signature = signature;
		this.access = access;
	}

	@Override
	public void accept(IVisitor v) {

	}

	@Override
	public String getSignature() {
		return this.signature;
	}

	@Override
	public String getAccess() {
		return this.access;
	}

}