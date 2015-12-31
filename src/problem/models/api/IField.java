package problem.models.api;

import problem.model.visitor.IModelTraverser;

public interface IField extends IModelTraverser{
	public String getType();
	public String getAccess();
	public String getName();
}
