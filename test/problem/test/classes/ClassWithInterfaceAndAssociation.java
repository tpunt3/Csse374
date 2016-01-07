package problem.test.classes;

public class ClassWithInterfaceAndAssociation implements IInterface{
	
	private EmptyClass clazz;
	
	public ClassWithInterfaceAndAssociation() {
		this.clazz = new EmptyClass();
	}

}
