package problem.model.visitor;

import problem.model.patternvisitor.IPatternVisitor;

public interface ITraverser {
	public void accept(IVisitor v);
	public void accept(IPatternVisitor v);
}
