package problem.models.api;

import problem.model.visitor.IModelTraverser;

public interface ISubMethod extends IModelTraverser {
	public String getClazzName();
	public String getMethodName();
	public String getArgs();
	public String getQualifiedClassName();
}
