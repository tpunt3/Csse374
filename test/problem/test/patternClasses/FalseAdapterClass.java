package problem.test.patternClasses;

public class FalseAdapterClass implements TargetInterface {
	private AdapteeClass adaptee;
	
	public FalseAdapterClass() {
		this.adaptee = adaptee;
	}

	@Override
	public void doSomething() {
	}

}
