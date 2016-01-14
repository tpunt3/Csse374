package problem.models.impl;

import java.util.ArrayList;

import problem.model.visitor.IModelVisitor;
import problem.models.api.IClass;
import problem.models.api.IMethod;
import problem.models.api.ISubMethod;

public class Method implements IMethod {

	private String access;
	private String name;
	private String args;
	private String returnType;
	private String signature;
	private IClass clazz;
	private ArrayList<ISubMethod> subMethods;
	
	public Method(String access, String name, String args, String returnType, IClass clazz, ArrayList<ISubMethod> sm) {
		this.access = access;
		this.name = name;
		this.args = args;
		this.returnType = returnType;
		this.clazz = clazz;
		this.subMethods = sm;
	}
	
	@Override
	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}
	
	@Override
	public String getReturnType(){
		return returnType;
	}

	@Override
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

	public ArrayList<ISubMethod> getSubMethods() {
		return subMethods;
	}

	public IClass getClazz() {
		return clazz;
	}

	public void setClazz(IClass clazz) {
		this.clazz = clazz;
	}

	@Override
	public void acceptSequence(IModelVisitor v, ISubMethod sm, int depth) {
		v.preVisit(this);
		
	}
}
