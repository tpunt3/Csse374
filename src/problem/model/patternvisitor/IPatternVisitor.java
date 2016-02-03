package problem.model.patternvisitor;

import problem.model.visitor.ITraverser;
import problem.model.visitor.IVisitMethod;
import problem.model.visitor.VisitType;

public interface IPatternVisitor {
	//public void singletonVisit(ITraverser t);
	public void visit(ITraverser t);
	public void postVisit(ITraverser t);
	
	public void addVisit(VisitType vType, Class clazz, IPatternVisitMethod m);
	public void removeVisit(VisitType vType, Class clazz);
}
