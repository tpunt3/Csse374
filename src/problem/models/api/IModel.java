package problem.models.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import problem.model.visitor.ITraverser;

public interface IModel extends ITraverser{
	public HashSet<IClass> getClasses();
	public void addClazz(IClass c);
	public IClass getClazz(String s);
	public ArrayList<IRelation> getRelations();
	public HashSet<String> getClassStrings();
	public ArrayList<String> getMethodStrings();
	public void replaceClass(IClass oldC, IClass newC);
	public void addRelation(IRelation r);
}
