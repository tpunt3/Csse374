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
import problem.test.tests.TestDesignParser;

public class Model implements IModel {

	private static Model uniqueInstance;
	
	private int callDepth;
	private HashSet<IClass> classes;
	private ArrayList<IRelation> relations;
	private Set<String> classStrings;
	private ArrayList<String> methodStrings;
	private ArrayList<String> classesToAdd;
	private ArrayList<String> classNames;

	private Model() {
		this.classes = new HashSet<IClass>();
		this.relations = new ArrayList<IRelation>();
		this.callDepth = 5;
		this.classStrings = new HashSet<String>();
		this.methodStrings = new ArrayList<String>();
		this.classesToAdd = new ArrayList<String>();
		this.classNames = new ArrayList<String>();
	}
	
	public static Model getInstance(){
		if(uniqueInstance == null){
			uniqueInstance = new Model();
		}
		return uniqueInstance;
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

	public Model(HashSet<IClass> classes) {
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

		// If you are running the UnitTesting JUNIT tests in ProjectGVTesting, uncomment this next
		// line and comment out the one above:
		 //boolean inClasses = true;
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
	public HashSet<IClass> getClasses() {
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
					String s = clazz.getName() + ":" + clazz.getName() + "[a]";
					this.classStrings.add(s);

					for (IMethod m : clazz.getMethods()) {
						if ((m.getName()).equals(sm.getMethodName())) {
							if (m.getArgs().equals(sm.getArgs())) {
								for (ISubMethod innerSM : m.getSubMethods()) {
									if (!innerSM.isVisited()) {
										String s2 = clazz.getName() + ":" + innerSM.getReturnType() +"="+ innerSM.getClazzName() + "."
												+ innerSM.getMethodName() + "(" + innerSM.getArgs() + ")";
										this.methodStrings.add(s2);
										
										//innerSM.setVisited(true);
										this.acceptSequence(v, innerSM, depth - 1);
									}
								}
								break;
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
	
	public void clearSD(){
		this.classStrings.clear();
		this.methodStrings.clear();
	}
	
	public void clearModel(){
		this.classes.clear();
		this.relations.clear();
		this.classStrings.clear();
		this.methodStrings.clear();
		this.classesToAdd.clear();
		this.classNames.clear();
	}

	@Override
	public void writeFile(IModelVisitor v) {
		v.preVisit(this);
		v.visit(this);
	}
}
