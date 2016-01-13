package problem.model.visitor;

public interface IModelTraverser {
	public void accept(IModelVisitor v);
	public void acceptSequence(IModelVisitor v);
}
