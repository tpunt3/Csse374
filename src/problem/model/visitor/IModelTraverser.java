package problem.model.visitor;

import problem.models.api.ISubMethod;

public interface IModelTraverser {
	public void accept(IModelVisitor v);
	public void acceptSequence(IModelVisitor v, ISubMethod sm, int depth);
}
