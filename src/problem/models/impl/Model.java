package problem.models.impl;

import java.util.Collection;

import problem.car.visitor.ITraverser;
import problem.car.visitor.IVisitor;
import problem.models.api.IElement;
import problem.models.api.IModel;

public class Model implements IModel, ITraverser{
	
	private Collection<IElement> elements;

	public Model(Collection<IElement> elements) {
		this.elements = elements;
	}

	@Override
	public Collection<IElement> getElements() {
		return this.elements;
	}

	@Override
	public void accept(IVisitor v) {
		
	}
	
	public String toString(){
		return null;
	}

}
