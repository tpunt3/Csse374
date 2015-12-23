package problem.models.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import problem.car.visitor.ITraverser;
import problem.car.visitor.IVisitor;
import problem.model.visitor.IModelTraverser;
import problem.model.visitor.IModelVisitor;
import problem.models.api.IClass;
import problem.models.api.IElement;
import problem.models.api.IModel;

public class Model implements IModel{
	
	private Collection<IClass> classes;
//	private Map<String, IClass> stringToClass = new HashMap<String, IClass>();
	
	public Model(){
		this.classes = new ArrayList<IClass>();
	}

	public Model(Collection<IClass> classes) {
		this.classes = classes;
	}

	@Override
	public Collection<IClass> getClasses() {
		return this.classes;
	}

	@Override
	public void accept(IModelVisitor v) {
		
	}
	
	public String toString(){
		return null;
	}
	
	public void addClazz(IClass clazz){
		classes.add(clazz);
	}
	
	public IClass getClazz(String key){
		for(IClass clazz : classes){
			if(clazz.getName().equals(key)){
				return clazz;
			}
		}
		return null;
	}

}
