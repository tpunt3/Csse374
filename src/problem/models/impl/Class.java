package problem.models.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import problem.model.visitor.IVisitor;
import problem.models.api.IClass;
import problem.models.api.IField;
import problem.models.api.IMethod;
import problem.models.api.ISubMethod;

public class Class implements IClass {

	private String name;
	private HashSet<IMethod> methods;
	private Collection<IField> fields;
	private boolean isClass;
	private boolean hasStaticField;
	private boolean hasPrivateConstructor;
	private boolean hasPublicStaticMethod;
	
	public Class(String name, boolean isClass) {
		this.isClass = isClass;
		this.name = name;
		this.methods = new HashSet<IMethod>();
		this.fields = new ArrayList<IField>();
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

	public boolean isSingleton() {
		return (this.hasStaticField && this.hasPrivateConstructor && this.hasPublicStaticMethod);
	}

	public void setHasStaticField(boolean hasStaticField) {
		this.hasStaticField = hasStaticField;
	}

	public void setHasPrivateConstructor(boolean hasPrivateConstructor) {
		this.hasPrivateConstructor = hasPrivateConstructor;
	}

	public void setHasPublicStaticMethod(boolean hasPublicStaticMethod) {
		this.hasPublicStaticMethod = hasPublicStaticMethod;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fields == null) ? 0 : fields.hashCode());
		result = prime * result + (isClass ? 1231 : 1237);
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
		if (isClass != other.isClass)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}


}
