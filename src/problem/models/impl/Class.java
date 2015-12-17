package problem.models.impl;

import problem.car.visitor.IVisitor;
import problem.models.api.IClass;

public class Class implements IClass {
	
	private String name;
	
	public Class(String name) {
		this.name = name;
	}

	@Override
	public void accept(IVisitor v) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		return this.name;
	}

}
