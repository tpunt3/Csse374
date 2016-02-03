package problem.model.detectors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import problem.model.decorators.AdapteeDecorator;
import problem.model.decorators.AdapterDecorator;
import problem.model.decorators.CompositeComponentDecorator;
import problem.model.decorators.CompositeDecorator;
import problem.model.decorators.LeafDecorator;
import problem.model.decorators.TargetDecorator;
import problem.models.api.IClass;
import problem.models.api.IField;
import problem.models.api.IModel;

public class CompositeDetector implements IPatternDetector {
	private IModel model;
	private ArrayList<IClass> composites;
	private ArrayList<IClass> components;
	private ArrayList<IClass> leaves;

	public CompositeDetector(IModel model) {
		this.model = model;
		this.composites = new ArrayList<IClass>();
		this.components = new ArrayList<IClass>();
		this.leaves = new ArrayList<IClass>();
	}

	@Override
	public void detectPatterns() {
		for (IClass c : this.model.getClasses()) {
			Map<String, Integer> fieldMap = new HashMap<String, Integer>();

			for (IField f : c.getFields()) {
				fieldMap.put(f.getType(), 0);
			}

			for (IField f : c.getFields()) {
				fieldMap.put(f.getType(), fieldMap.get(f.getType()) + 1);
			}

			for (IField f : c.getFields()) {
				if (f.getIsCollection() || fieldMap.get(f.getType()) > 1) {

					IClass component = null;
					for (IClass c2 : this.model.getClasses()) {
						if (c2.getName().equals(f.getType())) {
							component = c2;
							break;
						}
					}

					boolean isComposite = false;

					// now we need to see if component is in the hierarchy
					if (component != null && c != null) {
						isComposite = checkHierarchy(c, component, true);
					}

					if (isComposite) {
						this.composites.add(c);
						this.components.add(component);
						finish(c, component);
					}
				}
			}
		}

		for (IClass c : this.components) {
			this.model.replaceClass(c, new CompositeComponentDecorator(c));
		}
		for (IClass c : this.composites) {
			this.model.replaceClass(c, new CompositeDecorator(c));
		}
		for (IClass c : this.leaves) {
			this.model.replaceClass(c, new LeafDecorator(c));
		}
	}

	private void finish(IClass composite, IClass component) {
		for (IClass c : this.model.getClasses()) {

			if (checkHierarchy(c, composite, true)) {
				this.composites.add(c);
			}

			if (checkHierarchy(c, component, true)) {
				for (IClass c2 : this.model.getClasses()) {
					if (checkHierarchy(c2, c, true)) {
						if (!this.composites.contains(c)) {
							this.components.add(c);
						}
					} else {
						if (!this.composites.contains(c)) {
							this.leaves.add(c);
						}
					}
				}
			}
		}
	}

	private boolean checkHierarchy(IClass clazz, IClass fieldType, boolean first) {
		// here we need to make sure that the class which c extends/implements
		// is not the same as adaptee
		if ((clazz.getSuperClass().equals("") || clazz.getSuperClass().equals("Object"))
				&& clazz.getInterfaceList().isEmpty() && first) {
			return false;
		} else if (clazz.getSuperClass().equals("") || clazz.getSuperClass().equals("Object")) {

			// check if the fieldType is exactly equal to any of our interfaces
			for (String s : clazz.getInterfaceList()) {
				if (s.equals(fieldType.getName())) {
					return true;
				}
			}

			// check if the fieldType is equal to anything in the hierarchy of
			// our interfaces
			for (String s : clazz.getInterfaceList()) {
				IClass newInterface = null;
				for (IClass c : this.model.getClasses()) {
					if (c.getName().equals(s)) {
						newInterface = c;
						break;
					}
				}

				if (newInterface != null) {
					if (checkHierarchy(newInterface, fieldType, false)) {
						return true;
					}
				}
			}
			return false;

		} else if (clazz.getInterfaceList().isEmpty()) {
			// check that its superclass is not the same as adaptee
			if (clazz.getSuperClass().equals(fieldType.getName())) {
				return true;
			} else {
				IClass superClass = null;
				for (IClass c : this.model.getClasses()) {
					if (clazz.getSuperClass().equals(c.getName())) {
						superClass = c;
					}
				}
				if (superClass != null) {
					if (checkHierarchy(superClass, fieldType, false)) {
						return true;
					}
				}

				return false;
			}

		} else {
			return false;
		}
	}

}
