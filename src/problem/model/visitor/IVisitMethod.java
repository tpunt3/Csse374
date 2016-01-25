package problem.model.visitor;

@FunctionalInterface
public interface IVisitMethod {
	public void execute(ITraverser t);
}
