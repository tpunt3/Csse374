package problem.test.patternClasses;

public class AlmostSingleton {
	
	private static AlmostSingleton uniqueInstance = new AlmostSingleton();
	
	public AlmostSingleton(){
		
	}
	
	public static AlmostSingleton getInstance(){
		return uniqueInstance;
	}

}
