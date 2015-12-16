package problem.car.impl;

import problem.car.api.IBody;
import problem.car.visitor.ITraverser;
import problem.car.visitor.IVisitor;

public class Body implements IBody {
	private String type;
	private String material;
	
	public Body(String type, String material) {
		this.type = type;
		this.material = material;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public String getMaterial() {
		return material;
	}

	@Override
	public String toString() {
		return "Body [type=" + type + ", material=" + material + "]";
	}

	@Override
	public void accept(IVisitor v) {
		v.preVisit(this);
		v.visit(this);
		v.postVisit(this);
		
	}
}
