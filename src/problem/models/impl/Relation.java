package problem.models.impl;

import problem.car.visitor.IVisitor;
import problem.models.api.IRelation;

public class Relation implements IRelation{
	
	private String superClass;
	private String[] interfaces;

	public Relation(){
		
	}
	
	public Relation(String superClass) {
		this.superClass = superClass;
	}
	
	public Relation(String[] interfaces){
		this.interfaces = interfaces;
	}
	
	public Relation(String superClass, String[] i) {
		this.superClass = superClass;
		this.interfaces = i;
	}

	@Override
	public void accept(IVisitor v) {
		
	}

	@Override
	public String getSuperClass() {
		return this.superClass;
	}

	@Override
	public String[] getInterfaces() {
		return this.interfaces;
	}

}
