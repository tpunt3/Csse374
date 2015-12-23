package problem.models.impl;

import problem.model.visitor.IModelVisitor;
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
	public String getSuperClass() {
		return this.superClass;
	}

	@Override
	public String[] getInterfaces() {
		return this.interfaces;
	}

	@Override
	public void accept(IModelVisitor v) {
		// TODO Auto-generated method stub
		
	}

}
