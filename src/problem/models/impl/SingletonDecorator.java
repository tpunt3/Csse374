package problem.models.impl;

import java.util.Collection;

import problem.model.visitor.IVisitor;
import problem.models.api.IClass;
import problem.models.api.IField;
import problem.models.api.IMethod;

public class SingletonDecorator implements IClass {

	public SingletonDecorator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(IVisitor v) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<IMethod> getMethods() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<IField> getFields() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addMethod(IMethod m) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addField(IField f) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean getIsClass() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setHasStaticField(boolean hasStaticField) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setHasPrivateConstructor(boolean hasPrivateConstructor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setHasPublicStaticMethod(boolean hasPublicStaticMethod) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isSingleton() {
		// TODO Auto-generated method stub
		return false;
	}

}
