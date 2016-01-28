package problem.model.decorators;

import java.util.ArrayList;

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
							boolean isDecorator = false;
							if(component != null){
								isDecorator = findComponent(c, component);
							}
							if(isDecorator){
								this.decorators.add(c);
								this.components.add(component);
							}

						}
					}
				}
			}
		}
		for (IClass c : this.adapters){
			this.model.replaceClass(c, new AdapterDecorator(c));
		}
		for (IClass c : this.adaptees){
			this.model.replaceClass(c, new AdapteeDecorator(c));
		}
		for (IClass c : this.targets){
			this.model.replaceClass(c, new TargetDecorator(c));
		}
		

	}

}
