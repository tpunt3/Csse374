package problem.car.impl;

import problem.car.api.IEngine;
import problem.car.visitor.ITraverser;
import problem.car.visitor.IVisitor;

public class Engine implements IEngine{
	private int cylinder;
	private float capacity;

	public Engine(int cylinder, float capacity) {
		this.cylinder = cylinder;
		this.capacity = capacity;
	}

	@Override
	public int getCylinder() {
		return this.cylinder;
	}

	@Override
	public float getCapacity() {
		return this.capacity;
	}

	@Override
	public String toString() {
		return "Engine [cylinder=" + cylinder + ", capacity=" + capacity + "]";
	}

	@Override
	public void accept(IVisitor v) {
		v.preVisit(this);
		v.visit(this);
		v.postVisit(this);
		
	}
}
