package problem.test.patternClasses;

public class AdapterClass implements TargetInterface {
	private AdapteeClass adaptee;

	public AdapterClass(AdapteeClass adaptee) {
		this.adaptee = adaptee;
	}

}
