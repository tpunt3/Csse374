package problem.models.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import problem.model.patternvisitor.IPatternVisitor;
import problem.model.visitor.IVisitor;
import problem.models.api.IClass;
import problem.models.api.IField;
import problem.models.api.IMethod;

public class Class implements IClass {

	private String name;
	private HashSet<IMethod> methods;
	private Collection<IField> fields;
	private boolean isClass;
	private String signature;
	private String superClass;
	private ArrayList<String> interfaceList;
	
	public Class(String name, boolean isClass, String signature, String superClass, ArrayList<String> interfaceList) {
		this.isClass = isClass;
		this.name = name;
		this.methods = new HashSet<IMethod>();
		this.fields = new ArrayList<IField>();
		this.signature = signature;
		this.superClass = superClass;
		this.interfaceList = interfaceList;
	}

	public Class() {
		this.methods = new HashSet<IMethod>();
		this.fields = new ArrayList<IField>();
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
	public void accept(IVisitor v) {
		v.preVisit(this);
		v.visit(this);
		for (IField f : this.fields) {
			f.accept(v);
		}

		if (!this.fields.isEmpty()) {
			v.intermediateVisit(this);
		}

		for (IMethod m : this.methods) {
			m.accept(v);
		}
		v.postVisit(this);
	}

	@Override
	public boolean getIsClass() {
		return this.isClass;
	}
	
	@Override
	public String getSuperClass(){
		return this.superClass;
	}
	
	@Override
	public ArrayList<String> getInterfaceList(){
		return this.interfaceList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Class other = (Class) obj;
		if (fields == null) {
			if (other.fields != null)
				return false;
		} else if (!fields.equals(other.fields))
			return false;
		if (methods == null) {
			if (other.methods != null)
				return false;
		} else if (!methods.equals(other.methods))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	public String getSignature(){
		return this.signature;
	}

	@Override
	public void accept(IPatternVisitor v) {
		v.visit(this);
		for(IMethod m : this.methods){
			m.accept(v);
		}
		
		for(IField f : this.fields){
			f.accept(v);
		}
	}
}
