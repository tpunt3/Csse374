package problem.model.decorators;

import problem.models.api.IClass;

public abstract class ClassDecorator implements IClass {
	
	private String color;
	private String patternName;

	public String getColor(){
		return this.color;
	};
	
	public void setColor(String color){
		this.color = color;
	}
	
	public String getPatternName(){
		return this.patternName;
	};
	
	public void setPatternName(String patternName){
		this.patternName = patternName;
	}
	
}
