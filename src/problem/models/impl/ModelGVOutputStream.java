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
		String s = "digraph model{\n" + "rankdir = BT;";
	}
	
	@Override
	public void visit(IModel m) {
		// TODO Auto-generated method stub
		super.visit(m);
	}

	@Override
	public void postVisit(IModel m) {
		String s = "}";
	}
	
	@Override
	public void preVisit(IClass c) {
		String s = c.getName() + " [\n" + "shape=\"record\",";
	}

	@Override
	public void visit(IClass c) {
		String s = "label = " + c.getName() + "| ";
	}

	@Override
	public void postVisit(IClass c) {
		String s = "]";
	}

	@Override
	public void preVisit(IMethod m) {
	}

	@Override
	public void visit(IMethod m) {
		// TODO Auto-generated method stub
		super.visit(m);
	}

	@Override
	public void postVisit(IMethod m) {
		// TODO Auto-generated method stub
		super.postVisit(m);
	}

	@Override
	public void preVisit(IField f) {
	}

	@Override
	public void visit(IField f) {
		String s = f.getAccess() + " " + f.getType();
	}

	@Override
	public void postVisit(IField f) {
		// TODO Auto-generated method stub
		super.postVisit(f);
	}

	public ModelGVOutputStream() {
		// TODO Auto-generated constructor stub
	}

}
