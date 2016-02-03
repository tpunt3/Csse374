package problem.models.impl;

import problem.model.patternvisitor.IPatternVisitor;
import problem.model.visitor.IVisitor;
import problem.models.api.IField;
import problem.models.api.ISubMethod;

public class Field implements IField{
	
	private String type;
	private String access;
	private String name;
	private boolean isCollection;

	public Field(String type, String name, String access, boolean isCollection) {
		this.type = type;
		this.name = name;
		this.access = access;
		this.isCollection = isCollection;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getType() {
		return this.type;
	}

	@Override
	public String getAccess() {
		return this.access;
	}
	
	@Override
	public boolean getIsCollection(){
		return this.isCollection;
	}

	@Override
	public void accept(IVisitor v) {
		//v.preVisit(this);
		v.visit(this);
		//v.postVisit(this);
	}

	@Override
	public void accept(IPatternVisitor v) {
		v.visit(this);
	}

}
