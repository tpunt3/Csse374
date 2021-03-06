package problem.test.patternClasses;

public class ConcreteComponentDecorator extends ComponentDecorator {
	private ComponentInterface compInterface;
	
	public ConcreteComponentDecorator(ComponentInterface i){
		this.compInterface = i;
	}

	@Override
	public void getStuff() {
		super.getStuff();
	}
}
