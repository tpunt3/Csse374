package problem.models.impl;

import java.util.ArrayList;
import java.util.Collection;

import problem.model.visitor.IModelVisitor;
import problem.models.api.IClass;
import problem.models.api.IField;
import problem.models.api.IMethod;
import problem.models.api.IRelation;

public class Class implements IClass {
	
	private String name;
	private Collection<IMethod> methods;
	private Collection<IField> fields;
	private Collection<IRelation> relations;
	private boolean isClass;
	
	public Class(String name, boolean isClass) {
		this.isClass = isClass;
		this.name = name;
		this.methods = new ArrayList<IMethod>();
		this.fields = new ArrayList<IField>();
		this.relations = new ArrayList<IRelation>();
	}

	public Class() {
		this.methods = new ArrayList<IMethod>();
		this.fields = new ArrayList<IField>();
		this.relations = new ArrayList<IRelation>();
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Collection<IMethod> getMethods() {
		return this.methods;
	}

	@Override
	public Collection<IField> getFields() {
		return this.fields;
	}

	@Override
	public void addMethod(IMethod m) {
		this.methods.add(m);
	}

	@Override
	public void addField(IField f) {
		this.fields.add(f);
	}

	@Override
	public Collection<IRelation> getRelations() {
		return this.relations;
	}

	@Override
	public void addRelation(IRelation r) {
		this.relations.add(r);
	}

	@Override
	public void accept(IModelVisitor v) {
		v.preVisit(this);
		v.visit(this);
		for(IField f : this.fields){
			f.accept(v);
		}
		
		if(!this.fields.isEmpty()){
			v.intermediateVisit(this);
		}
		
		for(IMethod m : this.methods){
			m.accept(v);
		}
		v.postVisit(this);
	}

	@Override
	public boolean getIsClass() {
		return this.isClass;
	}

}
