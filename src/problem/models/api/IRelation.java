package problem.models.api;

import java.util.Map;

import problem.model.visitor.IModelTraverser;

public interface IRelation extends IModelTraverser{
	public String getSuperClass();
	public Map<String, String[]> getInterfaces();
	public Map<String, String> getSuperClasses();
	public void addSuperClass(String name, String superName);
	void addInterfaces(String name, String[] interfaceName);
}
