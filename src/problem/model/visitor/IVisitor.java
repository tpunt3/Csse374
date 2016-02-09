package problem.model.visitor;

import problem.models.api.ISubMethod;

public interface IVisitor {
	public void preVisit(ITraverser t);
	public void visit(ITraverser t);
	public void postVisit(ITraverser t);
	public void visitRelations(ITraverser t);
	public void intermediateVisit(ITraverser t);
	
	public void addVisit(VisitType vType, Class clazz, IVisitMethod m);
	public void removeVisit(VisitType vType, Class clazz);
	public void acceptSequence(IVisitor v, ISubMethod sm, int depth);
}
