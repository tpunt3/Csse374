package problem.models.api;

import problem.model.visitor.ITraverser;

public interface IRelation extends ITraverser{
	public void setName(String s);
	public String getName();
	public void setRelatedClass(String s);
	public String getRelatedClass();
	public void setType(RelationType t);
	public RelationType getType();
}
