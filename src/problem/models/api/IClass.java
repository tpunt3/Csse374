package problem.models.api;

import java.util.Collection;

import problem.model.visitor.IModelTraverser;

public interface IClass extends IModelTraverser {
	public String getName();
	public Collection<IMethod> getMethods();
	public Collection<IField> getFields();
	public void addMethod(IMethod m);
	public void addField(IField f);
	public boolean getIsClass();
	public void setHasStaticField(boolean hasStaticField);
	public void setHasPrivateConstructor(boolean hasPrivateConstructor);
	public void setHasPublicStaticMethod(boolean hasPublicStaticMethod);
	public boolean isSingleton();
}
