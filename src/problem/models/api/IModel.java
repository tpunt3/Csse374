package problem.models.api;

import java.util.Collection;

public interface IModel{
	public Collection<IClass> getClasses();
	public void addClazz(IClass c);
	public IClass getClazz(String s);
}
