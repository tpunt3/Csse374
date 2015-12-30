package problem.models.impl;

import java.io.IOException;
import java.io.OutputStream;

import javax.management.RuntimeErrorException;

import problem.model.visitor.ModelVisitorAdapter;
import problem.models.api.IClass;
import problem.models.api.IField;
import problem.models.api.IMethod;
import problem.models.api.IModel;

public class ModelGVOutputStream extends ModelVisitorAdapter{

	OutputStream out;
	
	public ModelGVOutputStream() {}
	
	public ModelGVOutputStream(OutputStream out) {
		super();
		this.out = out;
	}	
	
	private void write(String s) {
		try{
			s=s+"\n";
			out.write(s.getBytes());
		} catch (IOException e){
			new RuntimeException(e);
		}
	}

	@Override
	public void preVisit(IModel m) {
		String s = "digraph model{\n" + "rankdir = BT;";
		this.write(s);
	}
	
	@Override
	public void visit(IModel m) {
		super.visit(m);
	}

	@Override
	public void postVisit(IModel m) {
		String s = "}";
		this.write(s);
	}
	
	@Override
	public void preVisit(IClass c) {
		String s = String.format("%s[\nshape = \"record\",", c.getName());
		this.write(s);
	}

	@Override
	public void visit(IClass c) {
		String s = String.format("label = \"{%s| ", c.getName());
		this.write(s);
	}

	@Override
	public void postVisit(IClass c) {
		String s = "]";
		this.write(s);
	}

	@Override
	public void preVisit(IMethod m) {
	}

	@Override
	public void visit(IMethod m) {
		String s = String.format("%s%s(%s) : %s\\l", m.getAccess(), m.getName(), m.getArgs(), m.getReturnType());
		this.write(s);
	}

	@Override
	public void postVisit(IMethod m) {
		super.postVisit(m);
	}

	@Override
	public void preVisit(IField f) {
	}

	@Override
	public void visit(IField f) {
		String s = String.format("%s%s" + ":" +" %s\\l", f.getAccess(), f.getName(), f.getType());
		this.write(s);
	}

	@Override
	public void postVisit(IField f) {
	}

	

}
