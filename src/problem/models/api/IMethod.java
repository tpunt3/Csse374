package problem.models.api;

import problem.model.visitor.IModelTraverser;

public interface IMethod extends IModelTraverser{
	public String getAccess();
	public String getName();
	public String getArgs();
	public String getReturnType();
	public String getSignature();
}
