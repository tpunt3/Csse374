package problem.models.api;

import java.util.ArrayList;

import problem.model.visitor.ITraverser;

public interface IMethod extends ITraverser{
	public String getAccess();
	public String getName();
	public String getArgs();
	public String getReturnType();
	public String getSignature();
	public IClass getClazz();
	public ArrayList<ISubMethod> getSubMethods();
}
