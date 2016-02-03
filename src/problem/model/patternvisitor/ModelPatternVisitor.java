package problem.model.patternvisitor;

import java.util.ArrayList;
import java.util.HashSet;

import problem.model.decorators.SingletonDecorator;
import problem.model.visitor.ITraverser;
import problem.model.visitor.VisitType;
import problem.models.api.IClass;
import problem.models.api.IField;
import problem.models.api.IMethod;
import problem.models.api.IModel;
import problem.models.api.IRelation;
import problem.models.api.RelationType;
import problem.models.impl.Relation;

public class ModelPatternVisitor {

	PatternVisitor visitor;
	private HashSet<IClass> toReplace;

	public ModelPatternVisitor() {
		this.visitor = new PatternVisitor();
		this.toReplace = new HashSet<IClass>();
		this.setVisitModel();
		this.setVisitClass();
		this.setVisitMethod();
		this.setVisitField();
		this.setPostVisitModel();\
	}

	public void detect(IModel m) {
		ITraverser t = (ITraverser) m;
		t.accept(this.visitor);
	}

	private void setVisitModel() {

		this.visitor.addVisit(VisitType.Visit, IModel.class, (ITraverser t) -> {
			IModel model = (IModel) t;
			for (IClass c : model.getClasses()) {
				for (IClass c2 : model.getClasses()) {
					if (c2.getName().equals(c.getSuperClass())) {
						if (checkForSingleton(c2)) {
							this.toReplace.add(c);
						}
					}
				}
			}
		});
	}

	private void setVisitClass() {
		this.visitor.addVisit(VisitType.Visit, IClass.class, (ITraverser t) -> {
			IClass c = (IClass) t;

			boolean privateConstructor = false;
			boolean returnsSelf = false;
			boolean isEnum = false;

			if (c.getSignature() != null && c.getSignature().contains("Enum")) {
				isEnum = true;
			}

			for (IMethod m : c.getMethods()) {
				if ((m.getAccessNumber() == 9 || m.getAccessNumber() == 41)
						&& m.getReturnType().contains(m.getClazz().getName())) {
					returnsSelf = true;
				}

				if ((m.getAccess().equals("-") || m.getAccess().equals("#")) && m.getName().equals("init")) {
					privateConstructor = true;
				}
			}

			if (privateConstructor && returnsSelf && !isEnum) {
				this.toReplace.add(c);
			}

		});
	}

	private void setVisitMethod() {
		this.visitor.addVisit(VisitType.Visit, IMethod.class, (ITraverser t) -> {
			IMethod method = (IMethod) t;
		});
	}

	private void setVisitField() {
		this.visitor.addVisit(VisitType.Visit, IField.class, (ITraverser t) -> {
			IField field = (IField) t;
		});
	}

	private void setPostVisitModel() {
		this.visitor.addVisit(VisitType.PostVisit, IModel.class, (ITraverser t) -> {
			IModel m = (IModel) t;
			for (IClass c : this.toReplace) {
				m.replaceClass(c, new SingletonDecorator(c));
				IRelation relation = new Relation(c.getName(), c.getName(), RelationType.singleton);
				m.addRelation(relation);
			}
		});
	}

//	private void setSingletonVisit() {
//		this.visitor.addVisit(VisitType.SingletonPattern, IModel.class, (ITraverser t) -> {
//			IModel model = (IModel) t;
//			for (IClass c : model.getClasses()) {
//
//				boolean extendsSingleton = false;
//
//				for (IClass c2 : model.getClasses()) {
//					if (c2.getName().equals(c.getSuperClass())) {
//						if (checkForSingleton(c2)) {
//							extendsSingleton = true;
//						}
//					}
//				}
//
//				if (checkForSingleton(c) || extendsSingleton) {
//					this.toReplace.add(c);
//				}
//			}
//
//			for (IClass c : this.toReplace) {
//				model.replaceClass(c, new SingletonDecorator(c));
//				IRelation relation = new Relation(c.getName(), c.getName(), RelationType.singleton);
//				model.addRelation(relation);
//			}
//		});
//	}

	private boolean checkForSingleton(IClass c) {
		boolean privateConstructor = false;
		boolean returnsSelf = false;
		boolean isEnum = false;

		if (c.getSignature() != null && c.getSignature().contains("Enum")) {
			isEnum = true;
		}

		for (IMethod m : c.getMethods()) {
			if ((m.getAccessNumber() == 9 || m.getAccessNumber() == 41)
					&& m.getReturnType().contains(m.getClazz().getName())) {
				returnsSelf = true;
			}

			if ((m.getAccess().equals("-") || m.getAccess().equals("#")) && m.getName().equals("init")) {
				privateConstructor = true;
			}
		}

		if (privateConstructor && returnsSelf && !isEnum) {
			return true;
		}
		return false;
	}

}
