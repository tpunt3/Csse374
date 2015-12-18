package problem.models.impl;

import java.util.ArrayList;
import java.util.Collection;

import problem.car.visitor.IVisitor;
import problem.models.api.IClass;
import problem.models.api.IField;
import problem.models.api.IMethod;
import problem.models.api.IRelation;

public class Class implements IClass {
	
	private String name;
	private Collection<IMethod> methods;
	private Collection<IField> fields;
	private Collection<IRelation> relations;
	
	public Class(String name) {
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
	public void accept(IVisitor v) {
		// TODO Auto-generated method stub
		
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

}
