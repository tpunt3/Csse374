package problem.car.visitor;

import problem.car.api.IBody;
import problem.car.api.ICar;
import problem.car.api.IEngine;
import problem.car.api.IWheel;

public class VisitorAdapter implements IVisitor {

	@Override
	public void preVisit(ICar c) {
		
	}

	@Override
	public void preVisit(IBody b) {
		
	}

	@Override
	public void preVisit(IEngine e) {
		
	}

	@Override
	public void preVisit(IWheel w) {
		
	}

	@Override
	public void visit(ICar c) {
		
	}

	@Override
	public void visit(IBody b) {

	}

	@Override
	public void visit(IEngine e) {

	}

	@Override
	public void visit(IWheel w) {

	}

	@Override
	public void postVisit(ICar c) {

	}

	@Override
	public void postVisit(IBody b) {

	}

	@Override
	public void postVisit(IEngine e) {
	}

	@Override
	public void postVisit(IWheel w) {

	}

}
