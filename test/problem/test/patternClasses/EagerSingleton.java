package problem.test.patternClasses;

public class EagerSingleton {
		
		private static EagerSingleton uniqueInstance = new EagerSingleton();
		
		private EagerSingleton(){
			
		}
		
		public static EagerSingleton getInstance(){

			return uniqueInstance;
		}

}
