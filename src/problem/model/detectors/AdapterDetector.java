package problem.model.detectors;

import java.util.ArrayList;

import problem.model.decorators.AdapteeDecorator;
import problem.model.decorators.AdapterDecorator;
import problem.model.decorators.TargetDecorator;
import problem.models.api.IClass;
import problem.models.api.IField;
import problem.models.api.IMethod;
import problem.models.api.IModel;

public class AdapterDetector implements IPatternDetector {
	private IModel model;
	private ArrayList<IClass> adapters;
	private ArrayList<IClass> adaptees;
	private ArrayList<IClass> targets;

	public AdapterDetector(IModel model) {
		this.model = model;
		this.adapters = new ArrayList<IClass>();
		this.adaptees = new ArrayList<IClass>();
		this.targets = new ArrayList<IClass>();
	}

	@Override
	public void detectPatterns() {
		for (IClass c : this.model.getClasses()) {
			for (IMethod m : c.getMethods()) {
				String args = m.getArgs();

				String[] splitArgs = args.split(";");
				for (String s : splitArgs) {
					s = s.trim();
					for (IField f : c.getFields()) {
						if (s.equals(f.getType())) {
							// this has a field that is passed in through the
							// constructor

							IClass adaptee = null;
							for (IClass c2 : this.model.getClasses()) {
								if (c2.getName().equals(f.getType())) {
									adaptee = c2;
									break;
								}
							}

							// now we recurse to see if component is in the
							// hierarchy
							IClass target = null;

							boolean isAdapter = false;
							if (adaptee != null && c != null) {

								target = checkHierarchy(c, adaptee, true);

								if (target != null) {
									isAdapter = true;
								}
							}
							if (isAdapter) {
								this.adapters.add(c);
								this.adaptees.add(adaptee);
								this.targets.add(target);
							}
						}
					}
				}
			}
		}
		for (IClass c : this.adapters) {
			this.model.replaceClass(c, new AdapterDecorator(c));
		}
		for (IClass c : this.adaptees) {
			this.model.replaceClass(c, new AdapteeDecorator(c));
		}
		for (IClass c : this.targets) {
			this.model.replaceClass(c, new TargetDecorator(c));
		}
	}

	private IClass checkHierarchy(IClass adapter, IClass adaptee, boolean first) {
		// here we need to make sure that the class which c extends/implements
		// is not the same as adaptee
		if ((adapter.getSuperClass().equals("") || adapter.getSuperClass().equals("Object")) && adapter.getInterfaceList().isEmpty() && first) {
			return null;
		} else if (adapter.getSuperClass().equals("") || adapter.getSuperClass().equals("Object")) {
			// check that the none of the classes it implements are the same as
			// adaptee
			if (adapter.getInterfaceList().size() > 1 || adapter.getInterfaceList().size() == 0) {
				return adapter;
			} else {
				String interfaceName = adapter.getInterfaceList().get(0);
				if (!interfaceName.equals(adaptee.getName())) {

					IClass newInterface = null;
					for (IClass c : this.model.getClasses()) {
						if (c.getName().equals(interfaceName)) {
							newInterface = c;
							break;
						}
					}

					if (newInterface != null) {
						if (checkHierarchy(newInterface, adaptee, false) != null) {
							return newInterface;
						} else {
							return null;
						}
					}
				}
			}

		} else if (adapter.getInterfaceList().isEmpty()) {
			// check that its superclass is not the same as adaptee
			if (!adapter.getSuperClass().equals(adaptee.getName())) {

				IClass superClass = null;
				for (IClass c : this.model.getClasses()) {
					if (adapter.getSuperClass().equals(c.getName())) {
						superClass = c;
					}
				}
				// make sure none of its superclasses are that either
				if (superClass != null) {
					if (checkHierarchy(superClass, adaptee, false) != null) {
						return superClass;
					} else {
						return null;
					}
				}
			}else{
				//return not null?
			}

		} else {
			return null;
		}
		return null;
	}

}