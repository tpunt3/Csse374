package problem.model.visitor;

public interface IVisitor {
	public void preVisit(ITraverser t);
	public void visit(ITraverser t);
	public void postVisit(ITraverser t);
	public void visitRelations(ITraverser t);
	public void intermediateVisit(ITraverser t);
	
	public void addVisit(VisitType vType, Class clazz, IVisitMethod m);
	public void removeVisit(VisitType vType, Class clazz);
	
	
}
