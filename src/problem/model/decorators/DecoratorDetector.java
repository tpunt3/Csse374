package problem.model.decorators;

import java.util.ArrayList;

import problem.models.api.IClass;
import problem.models.api.IField;
import problem.models.api.IMethod;
import problem.models.api.IModel;
import problem.models.impl.SingletonDecorator;

public class DecoratorDetector implements IPatternDetector {
	private IModel model;
	private ArrayList<IClass> decorators;
	private ArrayList<IClass> components;

	public DecoratorDetector(IModel model) {
		this.model = model;
		this.decorators = new ArrayList<IClass>();
		this.components = new ArrayList<IClass>();
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
							// this is has a field that is passed in through the
							// constructor
							// now we need to check if that is in its hierarchy

							IClass component = null;
							for (IClass c2 : this.model.getClasses()) {
								if (c2.getName().equals(f.getType())) {
									component = c2;
								}
							}

							// now we recurse to see if component is in the
							// hierarchy
							boolean isDecorator = findComponent(c, component);
							if(isDecorator){
								this.decorators.add(c);
								this.components.add(component);
							}

						}
					}
				}
			}
		}
		for (IClass c : this.decorators){
			this.model.replaceClass(c, new DecoratorDecorator(c));
		}
		for (IClass c : this.components){
			this.model.replaceClass(c, new ComponentDecorator(c));
		}

	}

	private boolean findComponent(IClass decorator, IClass component) {

		if (decorator.getSuperClass().equals("") && decorator.getInterfaceList().isEmpty()) {
			return false;

		} else if (decorator.getSuperClass().equals("")) {
			for (String s : decorator.getInterfaceList()) {
				if (s.equals(component.getName())) {
					IClass newInterface = null;
					for (IClass c : this.model.getClasses()) {
						if (c.getName().equals(s)) {
							newInterface = c;
						}
					}
					this.decorators.add(newInterface);
					return true;
				}
			}

			for (String s : decorator.getInterfaceList()) {
				IClass newInterface = null;
				for (IClass c : this.model.getClasses()) {
					if (c.getName().equals(s)) {
						newInterface = c;
					}
				}
				
				if(newInterface != null){
					if(findComponent(newInterface, component)){
						this.decorators.add(newInterface);
						return true;
					}
				}
			}

		} else if (decorator.getInterfaceList().isEmpty()) {
			if (decorator.getSuperClass().equals(component.getName())) {
				IClass superClass = null;
				for (IClass c : this.model.getClasses()) {
					if (c.getName().equals(decorator.getSuperClass())) {
						superClass = c;
					}
				}
				return true;
			} else {
				IClass superClass = null;
				for (IClass c : this.model.getClasses()) {
					if (c.getName().equals(decorator.getSuperClass())) {
						superClass = c;
						break;
					}
				}

				if (superClass != null) {
					if (findComponent(superClass, component)) {
						this.decorators.add(superClass);
						return true;
					}
				}
			}

		} else {
			
			//recurse on the superclass
			if (decorator.getSuperClass().equals(component.getName())) {
				IClass superClass = null;
				for (IClass c : this.model.getClasses()) {
					if (c.getName().equals(decorator.getSuperClass())) {
						superClass = c;
					}
				}
				this.decorators.add(superClass);
				return true;
			} else {
				IClass superClass = null;
				for (IClass c : this.model.getClasses()) {
					if (c.getName().equals(decorator.getSuperClass())) {
						superClass = c;
					}
				}

				if (superClass != null) {
					if (findComponent(superClass, component)) {
						this.decorators.add(superClass);
						return true;
					}
				}
			}
			
			//recurse on the interfaces
			for (String s : decorator.getInterfaceList()) {
				if (s.equals(component.getName())) {
					IClass newInterface = null;
					for (IClass c : this.model.getClasses()) {
						if (c.getName().equals(s)) {
							newInterface = c;
						}
					}
					this.decorators.add(newInterface);
					return true;
				}
			}

			for (String s : decorator.getInterfaceList()) {
				IClass newInterface = null;
				for (IClass c : this.model.getClasses()) {
					if (c.getName().equals(s)) {
						newInterface = c;
					}
				}
				
				if(newInterface != null){
					if(findComponent(newInterface, component)){
						this.decorators.add(newInterface);
						return true;
					}
				}
			}
		}
		return false;
	}

}
