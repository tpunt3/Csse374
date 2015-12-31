package problem.models.api;

import java.util.Map;

public interface IRelation extends IElement{
	public String getSuperClass();
	public Map<String, String[]> getInterfaces();
	public Map<String, String> getSuperClasses();
	public void addSuperClass(String name, String superName);
	void addInterfaces(String name, String[] interfaceName);
}
