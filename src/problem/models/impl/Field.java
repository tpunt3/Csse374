package problem.models.impl;

import problem.model.visitor.IVisitor;
import problem.models.api.IField;
import problem.models.api.ISubMethod;

public class Field implements IField{
	
	private String type;
	private String access;
	private String name;

	public Field(String type, String name, String access) {
		this.type = type;
		this.name = name;
		this.access = access;
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
	public void accept(IVisitor v) {
		//v.preVisit(this);
		v.visit(this);
		//v.postVisit(this);
	}

}
