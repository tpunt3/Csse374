package problem.models.api;

import java.util.Map;

import problem.model.visitor.IModelTraverser;

public interface IRelation extends IModelTraverser{
	public void setName(String s);
	public String getName();
	public void setRelatedClass(String s);
	public String getRelatedClass();
	public void setType(RelationType t);
	public RelationType getType();
}
