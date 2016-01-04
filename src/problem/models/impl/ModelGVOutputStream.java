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
		String s = String.format("%s [\nshape=\"record\",\n", c.getName());
		this.write(s);
	}

	@Override
	public void visit(IClass c) {
		String s;
		if(!c.getIsClass()){
		s = String.format("label = \"{\\<\\<interface\\>\\>\\n%s| ", c.getName());
		}else{
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
			this.visitSuperClasses(r);
		}

		for (IRelation r : relations) {
			this.visitInterfaces(r);
		}
	}

	@Override
	public void visitSuperClasses(IRelation r) {
		String s = "";

		Set<String> keys = r.getSuperClasses().keySet();
		for (String k : keys) {
			String superClass = r.getSuperClasses().get(k);
			String[] superSplit = superClass.split("/");
			superClass = superSplit[superSplit.length - 1];
			s += "\n" + k + " -> " + superClass;
			s += " [arrowhead = \"empty\"];";
		}

		if (s != "") {
			this.write(s);
		}
	}

	@Override
	public void visitInterfaces(IRelation r) {
		String s = "";
		Set<String> keys = r.getInterfaces().keySet();
		for (String k : keys) {
			if (r.getInterfaces().get(k).length > 0) {
				String superClass = r.getInterfaces().get(k)[0];
				String[] superSplit = superClass.split("/");
				superClass = superSplit[superSplit.length - 1];
				s += "\n" + k + " -> " + superClass;

				for (int i = 1; i < r.getInterfaces().get(k).length; i++) {
					String interfaceName = r.getInterfaces().get(k)[i];
					String[] interfaceSplit = interfaceName.split("/");
					interfaceName = interfaceSplit[interfaceSplit.length - 1];
					s += ", " + interfaceName;
				}
				s += " [arrowhead = \"empty\", style = \"dashed\"];";
			}
		}
		if (s != "") {
			this.write(s);
		}
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
