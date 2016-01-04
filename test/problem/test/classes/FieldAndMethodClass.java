package problem.test.classes;

public class FieldAndMethodClass {
	
	public String testString;
	private int testInt;
	protected boolean testBoolean;

	public FieldAndMethodClass() {
	}
	
	public void doStuff(){
		System.out.println("software design");
	}
	
	public void doStuffWithArgs(String stuff, int number){
		System.out.println("YAY DOING STUFF");
	}

}
