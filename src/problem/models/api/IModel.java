package problem.models.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import problem.model.visitor.IModelTraverser;

public interface IModel extends IModelTraverser{
	public HashSet<IClass> getClasses();
	public void addClazz(IClass c);
	public IClass getClazz(String s);
	public ArrayList<IRelation> getRelations();
	public HashSet<String> getClassStrings();
	public ArrayList<String> getMethodStrings();
}
