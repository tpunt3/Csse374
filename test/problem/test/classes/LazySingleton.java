package problem.test.classes;

public class LazySingleton {
	
	private static LazySingleton uniqueInstance;
	
	private LazySingleton(){
		
	}
	
	public static LazySingleton getInstance(){
		if(uniqueInstance == null){
			uniqueInstance = new LazySingleton();
		}
		return uniqueInstance;
	}

}
