package problem.models.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import problem.asm.DesignParser;
import problem.model.visitor.IModelTraverser;
import problem.model.visitor.IModelVisitor;
import problem.models.api.IClass;
import problem.models.api.IModel;
import problem.models.api.IRelation;
import problem.models.api.RelationType;

public class Model implements IModel {

	private Collection<IClass> classes;
	private ArrayList<IRelation> relations;

	public Model() {
		this.classes = new ArrayList<IClass>();
		this.relations = new ArrayList<IRelation>();
	}

	public Model(Collection<IClass> classes) {
		this.classes = classes;
		this.relations = new ArrayList<IRelation>();
	}

	public ArrayList<IRelation> getRelations() {
		return relations;
	}

	public void setRelations(ArrayList<IRelation> relations) {
		this.relations = relations;
	}

	public void addRelation(IRelation r) {

		boolean inClasses = false;
		for (String s : DesignParser.CLASSES) {
			
			String[] split = s.split("\\.");
			s = split[split.length-1];
			
			if (r.getRelatedClass().equals(s)) {
				inClasses = true;
			}
		}

		if (inClasses) {
			if (this.relations.size() == 0) {
				this.relations.add(r);
				return;
			}

			for (int i = 0; i < this.relations.size(); i++) {
				if (r.equals(this.relations.get(i))) {
					return;
				}
				
				if(r.getName().equals(this.relations.get(i).getName()) && r.getRelatedClass().equals(this.relations.get(i).getRelatedClass())){
					if (r.getType().equals(RelationType.uses) && this.relations.get(i).getType().equals(RelationType.association)){
						return;
					}else if(r.getType().equals(RelationType.association) && this.relations.get(i).getType().equals(RelationType.uses)){
						this.relations.remove(i);
						this.relations.add(r);
						return;
					}
				}
			}
			this.relations.add(r);
		}
	}

	@Override
	public Collection<IClass> getClasses() {
		return this.classes;
	}

	@Override
	public void accept(IModelVisitor v) {
		v.preVisit(this);
		for (IClass c : this.classes) {
			c.accept(v);
		}
		v.visitRelations(this);
		v.postVisit(this);
	}

	public String toString() {
		return null;
	}

	public void addClazz(IClass clazz) {
		classes.add(clazz);
	}

	public IClass getClazz(String key) {
		for (IClass clazz : classes) {
			if (clazz.getName().equals(key)) {
				return clazz;
			}
		}
		return null;
	}

}
