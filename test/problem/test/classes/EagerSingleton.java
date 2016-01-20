package problem.test.classes;

public class EagerSingleton {
		
		private static EagerSingleton uniqueInstance = new EagerSingleton();
		
		private EagerSingleton(){
			
		}
		
		public static EagerSingleton getInstance(){

			return uniqueInstance;
		}

}
