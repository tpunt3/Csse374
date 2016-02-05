package problem.test.patternClasses;

public class MyComposite extends CompositeComponent{
	
	private CompositeComponent leaf1;
	private CompositeComponent leaf2;
	
	public MyComposite(){
		leaf1 = new Leaf();
		leaf2 = new Leaf();
	}
	
	
	
}
