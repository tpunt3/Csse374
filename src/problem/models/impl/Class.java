package problem.models.impl;

import java.util.Collection;

import problem.car.visitor.IVisitor;
import problem.models.api.IClass;
import problem.models.api.IField;
import problem.models.api.IMethod;

public class Class implements IClass {
	
	private String name;
	private Collection<IMethod> methods;
	private Collection<IField> fields;
	
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

	@Override
	public Collection<IMethod> getMethods() {
		return this.methods;
	}

	@Override
	public Collection<IField> getFields() {
		return this.fields;
	}

}
