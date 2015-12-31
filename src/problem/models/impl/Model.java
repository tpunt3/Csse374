package problem.models.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import problem.model.visitor.IModelTraverser;
import problem.model.visitor.IModelVisitor;
import problem.models.api.IClass;
import problem.models.api.IModel;
import problem.models.api.IRelation;

public class Model implements IModel{
	
	private Collection<IClass> classes;
	private Collection<IRelation> relations;
	
	public Model(){
		this.classes = new ArrayList<IClass>();
		this.relations = new ArrayList<IRelation>();
	}

	public Model(Collection<IClass> classes) {
		this.classes = classes;
		this.relations = new ArrayList<IRelation>();
	}

	public Collection<IRelation> getRelations() {
		return relations;
	}

	public void setRelations(Collection<IRelation> relations) {
		this.relations = relations;
	}
	
	public void addRelation(IRelation r){
		this.relations.add(r);
	}

	@Override
	public Collection<IClass> getClasses() {
		return this.classes;
	}

	@Override
	public void accept(IModelVisitor v) {
		v.preVisit(this);
		for(IClass c : this.classes){
			c.accept(v);
		}
		v.visitRelations(this);
		v.postVisit(this);
	}
	
	public String toString(){
		return null;
	}
	
	public void addClazz(IClass clazz){
		classes.add(clazz);
	}
	
	public IClass getClazz(String key){
		for(IClass clazz : classes){
			if(clazz.getName().equals(key)){
				return clazz;
			}
		}
		return null;
	}

}
