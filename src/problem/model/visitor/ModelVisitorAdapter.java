package problem.model.visitor;

import problem.models.api.IClass;
import problem.models.api.IField;
import problem.models.api.IMethod;
import problem.models.api.IModel;
import problem.models.api.IRelation;

public class ModelVisitorAdapter implements IModelVisitor{

	public ModelVisitorAdapter() {
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
		
	}

	@Override
	public void visit(IField f) {
		
	}

	@Override
	public void preVisit(IMethod m) {
		
	}

	@Override
	public void preVisit(IField f) {
		
	}

	@Override
	public void postVisit(IMethod m) {
		
	}

	@Override
	public void postVisit(IField f) {
		
	}

	@Override
	public void intermediateVisit(IClass C) {
		
	}

	@Override
	public void preVisit(IRelation r) {
		
	}	
	
	@Override
	public void visitSuperClasses(IRelation r) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitInterfaces(IRelation r) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postVisit(IRelation r) {
	}

	@Override
	public void visitRelations(IModel m) {
	}
}
