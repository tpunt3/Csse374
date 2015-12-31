package problem.models.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;

import javax.management.RuntimeErrorException;

import problem.model.visitor.ModelVisitorAdapter;
import problem.models.api.IClass;
import problem.models.api.IField;
import problem.models.api.IMethod;
import problem.models.api.IModel;
import problem.models.api.IRelation;

public class ModelGVOutputStream extends ModelVisitorAdapter {

	OutputStream out;

	public ModelGVOutputStream() {
	}

	public ModelGVOutputStream(OutputStream out) {
		super();
		this.out = out;
	}

	private void write(String s) {
		try {
			s = s + "\n";
			out.write(s.getBytes());
		} catch (IOException e) {
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
		String s = "}\"]";
		this.write(s);
	}

	@Override
	public void preVisit(IMethod m) {
	}

	@Override
	public void visit(IMethod m) {
		String s = String.format("%s %s(%s) : %s\\l", m.getAccess(), m.getName(), m.getArgs(), m.getReturnType());
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
		String s = String.format("%s%s" + ":" + " %s\\l", f.getAccess(), f.getName(), f.getType());
		this.write(s);
	}

	@Override
	public void postVisit(IField f) {
	}

	@Override
	public void preVisit(IRelation r) {
		// TODO Auto-generated method stub
		super.preVisit(r);
	}

	@Override
	public void visit(IRelation r) {
		// TODO Auto-generated method stub
		super.visit(r);
	}

	@Override
	public void visitRelations(IModel m) {
		ArrayList<IRelation> relations = (ArrayList<IRelation>) m.getRelations();
		String s = "edge [ arrowhead = \"empty\" ]";
		for (IRelation r : relations) {
			Set<String> keys = r.getSuperClasses().keySet();
			for (String k : keys) {
				s += "\n" + k + "->" + r.getSuperClasses().get(k);
			}
		}

		String t = "edge [ arrowhead = \"empty\" \nline = \"dashed\" ]";
		for (IRelation r : relations) {
			Set<String> keys = r.getInterfaces().keySet();
			for (String k : keys) {
				if (r.getInterfaces().get(k).length > 0) {
					t += "\n" + k + " -> "+r.getInterfaces().get(k)[0];

					for (int i = 1; i < r.getInterfaces().get(k).length; i++) {
						t += ", " + r.getInterfaces().get(k)[i];
					}
				}
			}
		}

		this.write(s);
		this.write(t);
	}

	@Override
	public void postVisit(IRelation r) {
		super.postVisit(r);
	}

	@Override
	public void intermediateVisit(IClass c) {
		String s = "|";
		this.write(s);
	}
}
