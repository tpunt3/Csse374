package problem.model.decorators;

import java.util.ArrayList;
import java.util.Collection;

import problem.model.patternvisitor.IPatternVisitor;
import problem.model.visitor.IVisitor;
import problem.models.api.IClass;
import problem.models.api.IField;
import problem.models.api.IMethod;

public class TargetDecorator extends ClassDecorator {

	private IClass thisClass;

	private String color;
	private String patternName;

	public TargetDecorator(IClass c) {
		this.thisClass = c;
		this.setColor("red");
		this.setPatternName("target");
	}

	@Override
	public void accept(IVisitor v) {
		v.preVisit(this);
		v.visit(this);
		for (IField f : this.thisClass.getFields()) {
			f.accept(v);
		}

		if (!this.thisClass.getFields().isEmpty()) {
			v.intermediateVisit(this);
		}

		for (IMethod m : this.thisClass.getMethods()) {
			m.accept(v);
		}
		v.postVisit(this);
	}

	@Override
	public String getName() {
		return this.thisClass.getName();
	}

	@Override
	public Collection<IMethod> getMethods() {
		return thisClass.getMethods();
	}

	@Override
	public Collection<IField> getFields() {
		return thisClass.getFields();
	}

	@Override
	public void addMethod(IMethod m) {
		thisClass.addMethod(m);
	}

	@Override
	public void addField(IField f) {
		thisClass.addField(f);
	}

	@Override
	public boolean getIsClass() {
		return thisClass.getIsClass();
	}

	@Override
	public String getSignature() {
		return thisClass.getSignature();
	}

	@Override
	public String getSuperClass() {
		return thisClass.getSuperClass();
	}

	@Override
	public ArrayList<String> getInterfaceList() {
		return thisClass.getInterfaceList();
	}

	@Override
	public void accept(IPatternVisitor v) {
		// TODO Auto-generated method stub

	}

}
