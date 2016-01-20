package problem.models.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import problem.model.visitor.ModelVisitorAdapter;
import problem.models.api.IClass;
import problem.models.api.IField;
import problem.models.api.IMethod;
import problem.models.api.IModel;
import problem.models.api.IRelation;

public class ModelGVOutputStream extends ModelVisitorAdapter {

	OutputStream out;
	StringBuilder s;

	public ModelGVOutputStream() {
	}

	public ModelGVOutputStream(OutputStream out) {
		super();
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
		String s = "digraph model{\n" + "rankdir = BT;\n";
		this.write(s);
	}

	@Override
	public void visit(IModel m) {
		super.visit(m);
	}

	@Override
	public void postVisit(IModel m) {
		String s = "\n}";
		this.write(s);
	}

	@Override
	public void preVisit(IClass c) {
		String s;
		if(c.isSingleton()){
			s = String.format("%s [\nshape=\"record\",color=blue\n", c.getName());
		} else{
			s = String.format("%s [\nshape=\"record\",\n", c.getName());
		}
		this.write(s);
	}

	@Override
	public void visit(IClass c) {
		String s;
		if (!c.getIsClass()) {
			s = String.format("label = \"{\\<\\<interface\\>\\>\\n%s| ", c.getName());
		} else if(c.isSingleton()){
			s = String.format("label = \"{%s\\n\\<\\<Singleton\\>\\>|", c.getName());
		} 
		else{
			s = String.format("label = \"{%s| ", c.getName());
		}
		this.write(s);
	}

	@Override
	public void postVisit(IClass c) {
		String s = "}\"\n];\n\n";
		this.write(s);
	}

	@Override
	public void preVisit(IMethod m) {
	}

	@Override
	public void visit(IMethod m) {
		String[] returnSplit = m.getReturnType().split("\\.");
		String returnType = returnSplit[returnSplit.length - 1];

		String s = String.format("%s %s(%s) : %s\\l", m.getAccess(), m.getName(), m.getArgs(), returnType);
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
		String s = String.format("%s %s" + ":" + " %s\\l", f.getAccess(), f.getName(), f.getType());
		this.write(s);
	}

	@Override
	public void postVisit(IField f) {
	}

	@Override
	public void preVisit(IRelation r) {
		super.preVisit(r);
	}

	@Override
	public void visitRelations(IModel m) {
		ArrayList<IRelation> relations = (ArrayList<IRelation>) m.getRelations();

		String comment = "//writing relations between classes now";
		this.write(comment);

		for (IRelation r : relations) {
			switch (r.getType()) {
			case association:
				visitAssociations(r);
				break;
			case uses:
				visitUses(r);
				break;
			case superclass:
				visitSuperClasses(r);
				break;
			case interfaces:
				visitInterfaces(r);
				break;
			}
		}
	}

	private void visitAssociations(IRelation r) {
		String s = String.format("\n%s -> %s [arrowhead = \"vee\"];", r.getName(), r.getRelatedClass());
		this.write(s);
	}

	private void visitUses(IRelation r) {
		String s = String.format("\n%s -> %s [arrowhead = \"vee\", style = \"dashed\"];", r.getName(),
				r.getRelatedClass());
		this.write(s);
	}

	@Override
	public void visitSuperClasses(IRelation r) {
		String[] relatedSplit = r.getRelatedClass().split("/");
		String split = relatedSplit[relatedSplit.length - 1];

		String s = String.format("\n%s -> %s [arrowhead = \"empty\"];", r.getName(), split);
		this.write(s);
	}

	@Override
	public void visitInterfaces(IRelation r) {
		String[] relatedSplit = r.getRelatedClass().split("/");
		String split = relatedSplit[relatedSplit.length - 1];

		String s = String.format("\n%s -> %s [arrowhead = \"empty\", style = \"dashed\"];", r.getName(), split);
		this.write(s);
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
