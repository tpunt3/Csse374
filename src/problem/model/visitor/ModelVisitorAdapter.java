package problem.model.visitor;

import problem.models.api.IClass;
import problem.models.api.IField;
import problem.models.api.IMethod;
import problem.models.api.IModel;

public class ModelVisitorAdapter implements IModelVisitor{

	public ModelVisitorAdapter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void preVisit(IModel m) {
	}

	@Override
	public void visit(IModel m) {
		
	}
	
	@Override
	public void postVisit(IModel m) {
	}

	@Override
	public void preVisit(IClass c) {
	}

	@Override
	public void visit(IClass c) {
	}
	
	@Override
	public void postVisit(IClass c) {
	}

	@Override
	public void visit(IMethod m) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(IField f) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preVisit(IMethod m) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preVisit(IField f) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postVisit(IMethod m) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postVisit(IField f) {
		// TODO Auto-generated method stub
		
	}

}
