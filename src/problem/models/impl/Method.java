package problem.models.impl;

import java.util.ArrayList;

import problem.model.patternvisitor.IPatternVisitor;
import problem.model.visitor.IVisitor;
import problem.models.api.IClass;
import problem.models.api.IMethod;
import problem.models.api.ISubMethod;

public class Method implements IMethod {

	private String access;
	private int accessNumber;
	private String name;
	private String args;
	private String returnType;
	private String signature;
	private IClass clazz;
	private ArrayList<ISubMethod> subMethods;
	
	public Method(String access, int accessNumber, String name, String args, String returnType, IClass clazz, ArrayList<ISubMethod> sm) {
		this.access = access;
		this.accessNumber = accessNumber;
		this.name = name;
		this.args = args;
		this.returnType = returnType;
		this.clazz = clazz;
		this.subMethods = sm;
	}
	
	@Override
	public int getAccessNumber() {
		return accessNumber;
	}

	public void setAccessNumber(int accessNumber) {
		this.accessNumber = accessNumber;
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
	public void accept(IVisitor v) {
		//v.preVisit(this);
		v.visit(this);
		//v.postVisit(this);
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((access == null) ? 0 : access.hashCode());
		result = prime * result + ((args == null) ? 0 : args.hashCode());
		result = prime * result + ((clazz == null) ? 0 : clazz.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Method other = (Method) obj;
		if (access == null) {
			if (other.access != null)
				return false;
		} else if (!access.equals(other.access))
			return false;
		if (args == null) {
			if (other.args != null)
				return false;
		} else if (!args.equals(other.args)){
			System.out.println("args arent equal");
			return false;
		}
		if (clazz == null) {
			if (other.clazz != null)
				return false;
		} else if (!clazz.equals(other.clazz))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public void accept(IPatternVisitor v) {
		v.visit(this);
	}

}
