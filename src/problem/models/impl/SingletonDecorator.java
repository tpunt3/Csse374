package problem.models.impl;

import java.util.Collection;

import problem.model.decorators.PatternType;
import problem.model.visitor.IVisitor;
import problem.models.api.IClass;
import problem.models.api.IField;
import problem.models.api.IMethod;

public class SingletonDecorator implements IClass {
	private IClass thisClass;

	public SingletonDecorator(IClass c) {
		this.thisClass = c;
		this.thisClass.addPattern(PatternType.Singleton);
	}

	@Override
	public void accept(IVisitor v) {
		thisClass.accept(v);
	}

	@Override
	public String getName() {
		return this.thisClass.getName();
	}

	@Override
	public Collection<IMethod> getMethods() {
		return thisClass.getMethods();
	}

	@Override
	public Collection<IField> getFields() {
		return thisClass.getFields();
	}

	@Override
	public void addMethod(IMethod m) {
		thisClass.addMethod(m);
	}

	@Override
	public void addField(IField f) {
		thisClass.addField(f);
	}

	@Override
	public boolean getIsClass() {
		return thisClass.getIsClass();
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	@Override
	public void addPattern(PatternType p) {
		thisClass.addPattern(p);
	}

	@Override
	public Collection<PatternType> getPatterns() {
		// TODO Auto-generated method stub
		return null;
	}

}
