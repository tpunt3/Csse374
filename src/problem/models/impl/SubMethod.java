package problem.models.impl;

import problem.model.patternvisitor.IPatternVisitor;
import problem.model.visitor.IVisitor;
import problem.models.api.IClass;
import problem.models.api.ISubMethod;

public class SubMethod implements ISubMethod {
	private String clazzName;
	private String name;
	private String args;
	private String qualifiedClassName;
	private String returnType;
	private boolean visited;

	public SubMethod(String qualifiedClassName, String clazzName, String name, String args, String returnType) {
		this.qualifiedClassName = qualifiedClassName;
		this.clazzName = clazzName;
		this.name = name;
		this.args = args;
		this.visited = false;
		this.returnType = returnType;
	}
	
	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
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
	public void accept(IVisitor v) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getReturnType() {
		return this.returnType;
	}

	@Override
	public void accept(IPatternVisitor v) {
		// TODO Auto-generated method stub
		
	}

}
