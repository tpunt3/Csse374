package problem.test.patternClasses;

public class MyComposite implements ICompositeComponent{
	
	private Leaf leaf1;
	private Leaf leaf2;
	
	public MyComposite(){
		leaf1 = new Leaf();
		leaf2 = new Leaf();
	}
	
	
	
}
