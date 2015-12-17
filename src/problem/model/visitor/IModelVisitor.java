package problem.model.visitor;

import problem.models.api.IClass;
import problem.models.api.IField;
import problem.models.api.IMethod;
import problem.models.api.IModel;

public interface IModelVisitor {
	public void preVisit(IModel m);

	public void visit(IModel m);
	public void visit(IClass c);
	public void visit(IMethod m);
	public void visit(IField f);
	
	public void postVisit(IModel m);
}
