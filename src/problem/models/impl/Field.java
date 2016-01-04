package problem.models.impl;

import problem.model.visitor.IModelVisitor;
import problem.models.api.IField;

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
	public void accept(IModelVisitor v) {
		v.preVisit(this);
		v.visit(this);
		v.postVisit(this);
	}

}
