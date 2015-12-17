package problem.models.impl;

import java.io.OutputStream;

import problem.model.visitor.ModelVisitorAdapter;
import problem.models.api.IClass;
import problem.models.api.IField;
import problem.models.api.IMethod;
import problem.models.api.IModel;

public class ModelGVOutputStream extends ModelVisitorAdapter{
	
	OutputStream out;

	public ModelGVOutputStream(OutputStream out) {
		this.out = out;
	}

	@Override
	public void preVisit(IModel m) {
		// TODO Auto-generated method stub
		super.preVisit(m);
	}

	@Override
	public void visit(IModel m) {
		// TODO Auto-generated method stub
		super.visit(m);
	}

	@Override
	public void visit(IClass c) {
		// TODO Auto-generated method stub
		super.visit(c);
	}

	@Override
	public void visit(IMethod m) {
		// TODO Auto-generated method stub
		super.visit(m);
	}

	@Override
	public void visit(IField f) {
		// TODO Auto-generated method stub
		super.visit(f);
	}

	@Override
	public void postVisit(IModel m) {
		// TODO Auto-generated method stub
		super.postVisit(m);
	}

	public ModelGVOutputStream() {
		// TODO Auto-generated constructor stub
	}

}
