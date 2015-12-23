package problem.models.impl;

import problem.car.visitor.IVisitor;
import problem.model.visitor.IModelVisitor;
import problem.models.api.IField;

public class Field implements IField{
	
	private String type;
	private String access;
	
	

	public Field(String type, String access) {
		this.type = type;
		this.access = access;
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
		// TODO Auto-generated method stub
		
	}

}
