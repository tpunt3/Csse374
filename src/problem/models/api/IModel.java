package problem.models.api;

import java.util.Collection;

import problem.model.visitor.IModelTraverser;

public interface IModel extends IModelTraverser{
	public Collection<IClass> getClasses();
	public void addClazz(IClass c);
	public IClass getClazz(String s);
	public Collection getRelations();
}
