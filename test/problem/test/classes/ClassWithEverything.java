package problem.test.classes;

public class ClassWithEverything extends EmptyClass implements IInterface{
	
	private String name;
	
	public ClassWithEverything(){
	}
	
	public void setName(String name){
		this.name = name + "loves csse374";
	}
	
	public String getName(){
		return this.name;
	}

}
