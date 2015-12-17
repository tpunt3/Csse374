package problem.models.impl;

import problem.car.visitor.IVisitor;
import problem.models.api.IRelation;

public class Relation implements IRelation{
	
	private String relation;

	public Relation(String relation) {
		this.relation = relation;
	}

	@Override
	public void accept(IVisitor v) {
		
	}

	@Override
	public String getRelations() {
		return this.relation;
	}

}
