package problem.models.api;

import java.util.Map;

import problem.model.visitor.IModelTraverser;

public interface IRelation extends IModelTraverser{
	public String getSuperClass();
	public Map<String, String[]> getInterfaces();
	public Map<String, String> getSuperClasses();
	public Map<String, String[]> getUses();
	public Map<String, String[]> getAssociations();
	public void addSuperClass(String name, String superName);
	public void addInterfaces(String name, String[] interfaceName);
	public void addUses(String name, String[] usesName);
	public void addAssociations(String name, String[] associationsName);
}
