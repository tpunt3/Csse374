package problem.models.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import problem.car.visitor.ITraverser;
import problem.car.visitor.IVisitor;
import problem.models.api.IClass;
import problem.models.api.IElement;
import problem.models.api.IModel;

public class Model implements IModel, ITraverser{
	
	private Collection<IClass> classes;
	private Map<String, IClass> stringToClass = new HashMap<String, IClass>();
	
	public Model(){
		
	}

	public Model(Collection<IClass> classes) {
		this.classes = classes;
		
	}

	@Override
	public Collection<IClass> getClasses() {
		return this.classes;
	}

	@Override
	public void accept(IVisitor v) {
		
	}
	
	public String toString(){
		return null;
	}

}
