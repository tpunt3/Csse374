package problem.models.impl;

import java.io.IOException;
import java.io.OutputStream;

import problem.model.visitor.ModelVisitorAdapter;
import problem.models.api.IClass;
import problem.models.api.IField;
import problem.models.api.IMethod;
import problem.models.api.IModel;
import problem.models.api.IRelation;

public class ModelSDOutputStream extends ModelVisitorAdapter{

	OutputStream out;
	StringBuilder s;
	
	public ModelSDOutputStream(OutputStream out) {
		this.out = out;
	}

	private void write(String s) {
		try {
			out.write(s.getBytes());
		} catch (IOException e) {
			new RuntimeException(e);
		}
	}
	
	@Override
	public void preVisit(IModel m) {
		Object [] classes = m.getClassStrings().toArray();
		String s = "";
		for(int i = m.getClassStrings().size() - 1; i >= 0; i--){
			s += classes[i]+"\n";
		}
		
		s +="\n";
		this.write(s);
	}

	@Override
	public void visit(IModel m) {
		String s = "";
		for(int i = 0; i < m.getMethodStrings().size(); i++){
			s += m.getMethodStrings().get(i).toString()+"\n";
		}
		this.write(s);
	}

	@Override
	public void postVisit(IModel m) {
		// TODO Auto-generated method stub
		super.postVisit(m);
	}

	@Override
	public void preVisit(IClass c) {
		// TODO Auto-generated method stub
		super.preVisit(c);
	}

	@Override
	public void visit(IClass c) {
		// TODO Auto-generated method stub
		super.visit(c);
	}

	@Override
	public void postVisit(IClass c) {
		// TODO Auto-generated method stub
		super.postVisit(c);
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
	public void preVisit(IMethod m) {
		String s = String.format("%s:%s", m.getClazz().getName(), m.getClazz());
		this.write(s);
	}

	@Override
	public void preVisit(IField f) {
		// TODO Auto-generated method stub
		super.preVisit(f);
	}

	@Override
	public void postVisit(IMethod m) {
		// TODO Auto-generated method stub
		super.postVisit(m);
	}

	@Override
	public void postVisit(IField f) {
		// TODO Auto-generated method stub
		super.postVisit(f);
	}

	@Override
	public void intermediateVisit(IClass C) {
		// TODO Auto-generated method stub
		super.intermediateVisit(C);
	}

	@Override
	public void preVisit(IRelation r) {
		// TODO Auto-generated method stub
		super.preVisit(r);
	}

	@Override
	public void visitSuperClasses(IRelation r) {
		// TODO Auto-generated method stub
		super.visitSuperClasses(r);
	}

	@Override
	public void visitInterfaces(IRelation r) {
		// TODO Auto-generated method stub
		super.visitInterfaces(r);
	}

	@Override
	public void postVisit(IRelation r) {
		// TODO Auto-generated method stub
		super.postVisit(r);
	}

	@Override
	public void visitRelations(IModel m) {
		// TODO Auto-generated method stub
		super.visitRelations(m);
	}
}
