package problem.models.api;

import problem.model.visitor.ITraverser;

public interface IField extends ITraverser{
	public String getType();
	public String getAccess();
	public String getName();
	public boolean getIsCollection();
}
