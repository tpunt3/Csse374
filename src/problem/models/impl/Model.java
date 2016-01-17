package problem.models.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import problem.asm.DesignParser;
import problem.model.visitor.IModelVisitor;
import problem.models.api.IClass;
import problem.models.api.IMethod;
import problem.models.api.IModel;
import problem.models.api.IRelation;
import problem.models.api.ISubMethod;
import problem.models.api.RelationType;

public class Model implements IModel {

	private int callDepth;
	private ArrayList<IClass> classes;
	private ArrayList<IRelation> relations;
	private Set<String> classStrings;
	private ArrayList<String> methodStrings;
	private ArrayList<String> classesToAdd;
	private ArrayList<String> classNames;

	public Model() {
		this.classes = new ArrayList<IClass>();
		this.relations = new ArrayList<IRelation>();
		this.callDepth = 5;
		this.classStrings = new HashSet<String>();
		this.methodStrings = new ArrayList<String>();
		this.classesToAdd = new ArrayList<String>();
		this.classNames =  new ArrayList<String>();
	}

	public ArrayList<String> getMethodStrings() {
		return methodStrings;
	}

	public HashSet<String> getClassStrings() {
		return (HashSet<String>) classStrings;
	}

	public int getCallDepth() {
		return callDepth;
	}

	public void setCallDepth(int callDepth) {
		this.callDepth = callDepth;
	}

	public Model(ArrayList<IClass> classes) {
		this.classes = classes;
		this.relations = new ArrayList<IRelation>();
	}

	public ArrayList<IRelation> getRelations() {
		return relations;
	}

	public void setRelations(ArrayList<IRelation> relations) {
		this.relations = relations;
	}

	public void addRelation(IRelation r) {

		boolean inClasses = false;

		// If you are running the UnitTesting JUNIT tests, uncomment this next
		// line:
		// boolean inClasses = true;
		for (String s : DesignParser.CLASSES) {

			String[] split = s.split("\\.");
			s = split[split.length - 1];

			if (r.getRelatedClass().equals(s)) {
				inClasses = true;
			}
		}
		// this is logic for when to add a relation to our UML and when not to
		if (inClasses) {

			if (r.getName().equals(r.getRelatedClass())) {
				return;
			}

			if (this.relations.size() == 0) {
				this.relations.add(r);
				return;
			}

			for (int i = 0; i < this.relations.size(); i++) {
				if (r.equals(this.relations.get(i))) {
					return;
				}

				if (r.getName().equals(this.relations.get(i).getName())
						&& r.getRelatedClass().equals(this.relations.get(i).getRelatedClass())) {
					if (r.getType().equals(RelationType.uses)
							&& this.relations.get(i).getType().equals(RelationType.association)) {
						return;
					} else if (r.getType().equals(RelationType.association)
							&& this.relations.get(i).getType().equals(RelationType.uses)) {
						this.relations.remove(i);
						this.relations.add(r);
						return;
					}
				}
			}
			this.relations.add(r);
		}
	}

	@Override
	public ArrayList<IClass> getClasses() {
		return this.classes;
	}

	public void getClassNames() {
		for (IClass c : this.classes) {
			this.classNames.add(c.getName());
		}
	}

	@Override
	public void accept(IModelVisitor v) {
		v.preVisit(this);
		for (IClass c : this.classes) {
			c.accept(v);
		}
		v.visitRelations(this);
		v.postVisit(this);
	}

	public String toString() {
		return null;
	}

	public void addClazz(IClass clazz) {
		classes.add(clazz);
	}

	public IClass getClazz(String key) {
		for (IClass clazz : classes) {
			if (clazz.getName().equals(key)) {
				return clazz;
			}
		}
		return null;
	}

	public String[] findNewClasses(ISubMethod sm, int depth) {
		getClassNames();

		// System.out.println("subMethod className: " + sm.getClazzName() + "
		// submethod methodName: " + sm.getMethodName());

		if (depth > 0) {
			for (IClass clazz : classes) {
				if (clazz.getName().equals(sm.getClazzName())) {
					this.classesToAdd.add(sm.getQualifiedClassName());
					for (IMethod m : clazz.getMethods()) {
						if ((m.getName()).equals(sm.getMethodName())) {
							if (m.getArgs().equals(sm.getArgs())) {
								this.classesToAdd.remove(sm.getQualifiedClassName());
								for (ISubMethod innerSM : m.getSubMethods()) {
									if (sm.getClazzName().equals("ArrayList")) {
										// System.out.println(innerSM.getQualifiedClassName());
									}
									if (!this.classNames.contains(innerSM.getClazzName())) {
										this.classesToAdd.add(innerSM.getQualifiedClassName());
									}
									this.findNewClasses(innerSM, depth - 1);
								}
							}
						}
					}
				}
			}
		}
		return this.classesToAdd.toArray(new String[classesToAdd.size()]);
	}

	@Override
	public void acceptSequence(IModelVisitor v, ISubMethod sm, int depth) {
		if (depth > 0) {
			for (IClass clazz : classes) {
				if (clazz.getName().equals(sm.getClazzName())) {
					// System.out.println("class: " + clazz.getName() + "
					// submethod class: " + sm.getClazzName());
					String s = clazz.getName() + ":" + clazz.getName() + "[a]";
					this.classStrings.add(s);

					for (IMethod m : clazz.getMethods()) {
						if ((m.getName()).equals(sm.getMethodName())) {
							if (m.getArgs().equals(sm.getArgs())) {
								for (ISubMethod innerSM : m.getSubMethods()) {
									// System.out.println(innerSM.getMethodName()
									// + innerSM.getArgs());
									String s2 = clazz.getName() + ":" + innerSM.getClazzName() + "."
											+ innerSM.getMethodName() + "(" + innerSM.getArgs() + ")";
									this.methodStrings.add(s2);
									this.acceptSequence(v, innerSM, depth - 1);
								}

							}
						}
					}
				}
				String s = clazz.getName() + ":" + clazz.getName() + "[a]";
				this.classStrings.add(s);
			}
		}
		return;
	}

	@Override
	public void writeFile(IModelVisitor v) {
		v.preVisit(this);
		v.visit(this);
	}
}
