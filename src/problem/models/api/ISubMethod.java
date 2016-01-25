package problem.models.api;

import problem.model.visitor.ITraverser;

public interface ISubMethod extends ITraverser {
	public String getClazzName();
	public String getMethodName();
	public String getArgs();
	public String getQualifiedClassName();
	public boolean isVisited();
	public void setVisited(boolean b);
	public String getReturnType();
}
