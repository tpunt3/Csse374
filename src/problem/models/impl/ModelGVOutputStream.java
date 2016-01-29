package problem.models.impl;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import problem.model.decorators.AdapteeDecorator;
import problem.model.decorators.AdapterDecorator;
import problem.model.decorators.ComponentDecorator;
import problem.model.decorators.DecoratorDecorator;
import problem.model.decorators.PatternType;
import problem.model.decorators.SingletonDecorator;
import problem.model.decorators.TargetDecorator;
import problem.model.detectors.IPatternDetector;
import problem.model.detectors.SingletonDetector;
import problem.model.visitor.ITraverser;
import problem.model.visitor.Visitor;
import problem.model.visitor.VisitType;
import problem.models.api.IClass;
import problem.models.api.IField;
import problem.models.api.IMethod;
import problem.models.api.IModel;
import problem.models.api.IRelation;

public class ModelGVOutputStream extends FilterOutputStream {

	OutputStream out;
	StringBuilder s;
	Visitor visitor;

	public ModelGVOutputStream(OutputStream out) {
		super(out);
		this.out = out;
		this.visitor = new Visitor();
		this.setPreVisitModel();
		this.setPostVisitModel();
		this.setPreVisitClass();
		this.setVisitClass();
		this.setPostVisitClass();
		this.setVisitMethod();
		this.setVisitField();
		this.setIntermediateVisit();
		this.setRelationVisit();
	}
	
	public void write(IModel m){
		ITraverser t = (ITraverser) m;
		t.accept(this.visitor);
	}
	
	private void write(String s) {
		try {
			out.write(s.getBytes());
		} catch (IOException e) {
			new RuntimeException(e);
		}
	}

	private void setPreVisitModel() {
		this.visitor.addVisit(VisitType.PreVisit, IModel.class, (ITraverser t) ->{
			IModel m = (IModel) t;
			String s = "digraph model{\n" + "rankdir = BT;\n";
			this.write(s);	
		});
	}

	private void setPostVisitModel() {
		this.visitor.addVisit(VisitType.PostVisit, IModel.class, (ITraverser t) ->{
			IModel m = (IModel) t;
			String s = "\n}";
			this.write(s);	
		});
	}

	private void setPreVisitClass() {
		this.visitor.addVisit(VisitType.PreVisit, IClass.class, (ITraverser t) ->{
			IClass c = (IClass) t;
			String s;
			if(c instanceof SingletonDecorator){
				s = String.format("%s [\nshape=\"record\",color=blue\n", c.getName());
			}else if(c instanceof DecoratorDecorator || c instanceof ComponentDecorator){
				s = String.format("%s [\nshape=\"record\",color=green\n", c.getName());
			}else if(c instanceof AdapterDecorator || c instanceof AdapteeDecorator || c instanceof TargetDecorator){
				s = String.format("%s [\nshape=\"record\",color=red\n", c.getName());
			}else{
				s = String.format("%s [\nshape=\"record\",\n", c.getName());
			}
			this.write(s);
		});
	}

	private void setVisitClass() {
		this.visitor.addVisit(VisitType.Visit, IClass.class, (ITraverser t) ->{
			IClass c = (IClass) t;
			
			String s;
			if (!c.getIsClass()) {
				if(c instanceof ComponentDecorator){
					s = String.format("label = \"{\\<\\<interface\\>\\>\\n%s\\n\\<\\<component\\>\\>| ", c.getName());
				}else if(c instanceof TargetDecorator){
					s = String.format("label = \"{\\<\\<interface\\>\\>\\n%s\\n\\<\\<target\\>\\>| ", c.getName());
				}else if(c instanceof AdapteeDecorator){
					s = String.format("label = \"{\\<\\<interface\\>\\>\\n%s\\n\\<\\<adaptee\\>\\>| ", c.getName());
				}else{
					s = String.format("label = \"{\\<\\<interface\\>\\>\\n%s| ", c.getName());
				}
			} else if(c instanceof SingletonDecorator){
				s = String.format("label = \"{%s\\n\\<\\<Singleton\\>\\>|", c.getName());
			} else if(c instanceof DecoratorDecorator){
				s = String.format("label = \"{%s\\n\\<\\<decorator\\>\\>|", c.getName());
			}else if(c instanceof ComponentDecorator){
				s = String.format("label = \"{%s\\n\\<\\<component\\>\\>|", c.getName());
			}else if(c instanceof AdapterDecorator){
				s = String.format("label = \"{%s\\n\\<\\<adapter\\>\\>|", c.getName());
			}else if(c instanceof AdapteeDecorator){
				s = String.format("label = \"{%s\\n\\<\\<adaptee\\>\\>|", c.getName());
			}else if(c instanceof TargetDecorator){
				s = String.format("label = \"{%s\\n\\<\\<target\\>\\>|", c.getName());
			}
			else{
				s = String.format("label = \"{%s| ", c.getName());
			}
			this.write(s);
		});
	}

	private void setPostVisitClass() {
		this.visitor.addVisit(VisitType.PostVisit, IClass.class, (ITraverser t) ->{
			IClass c = (IClass) t;
			String s = "}\"\n];\n\n";
			this.write(s);
		});
	}

	private void setVisitMethod() {
		this.visitor.addVisit(VisitType.Visit, IMethod.class, (ITraverser t) ->{
			
			IMethod m = (IMethod) t;
			String[] returnSplit = m.getReturnType().split("\\.");
			String returnType = returnSplit[returnSplit.length - 1];
	
			String s = String.format("%s %s(%s) : %s\\l", m.getAccess(), m.getName(), m.getArgs(), returnType);
			this.write(s);
		});
	}

	private void setVisitField() {
		this.visitor.addVisit(VisitType.Visit, IField.class, (ITraverser t) ->{
			IField f = (IField) t;
			String s = String.format("%s %s" + ":" + " %s\\l", f.getAccess(), f.getName(), f.getType());
			this.write(s);
		});
	}
	
	private void setIntermediateVisit(){
		this.visitor.addVisit(VisitType.IntermediateVisit, IClass.class, (ITraverser t) ->{
			String s = "|";
			this.write(s);
		});
	}
	
	private void setRelationVisit(){
		this.visitor.addVisit(VisitType.RelationVisit, IModel.class, (ITraverser t) ->{
			IModel m = (IModel) t;
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
				case decorates:
					visitDecorates(r);
					break;
				}
			}
		});
	}

	private void visitDecorates(IRelation r){
		String s = String.format("\n%s -> %s [arrowhead = \"vee\", label = \"decorates\"];", r.getName(), r.getRelatedClass());
		this.write(s);
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

	private void visitSuperClasses(IRelation r) {
		String[] relatedSplit = r.getRelatedClass().split("/");
		String split = relatedSplit[relatedSplit.length - 1];

		String s = String.format("\n%s -> %s [arrowhead = \"empty\"];", r.getName(), split);
		this.write(s);
	}

	private void visitInterfaces(IRelation r) {
		String[] relatedSplit = r.getRelatedClass().split("/");
		String split = relatedSplit[relatedSplit.length - 1];

		String s = String.format("\n%s -> %s [arrowhead = \"empty\", style = \"dashed\"];", r.getName(), split);
		this.write(s);
	}
}
