package problem.model.detectors;

import java.util.ArrayList;

import problem.model.decorators.SingletonDecorator;
import problem.models.api.IClass;
import problem.models.api.IMethod;
import problem.models.api.IModel;
import problem.models.api.IRelation;
import problem.models.api.RelationType;
import problem.models.impl.Relation;

public class SingletonDetector implements IPatternDetector {
	private IModel model;
	private ArrayList<IClass> toReplace;
	boolean instanceRequired;
	
	public SingletonDetector(IModel model) {
		this.model = model;
		this.toReplace = new ArrayList<IClass>();
	}
	
	public SingletonDetector(IModel model, boolean instanceRequired) {
		this.model = model;
		this.toReplace = new ArrayList<IClass>();
		this.instanceRequired = instanceRequired;
	}
	
	@Override
	public void detectPatterns(){
		for(IClass c : model.getClasses()){
			
			boolean extendsSingleton = false;
			
			
			
			for(IClass c2 : model.getClasses()){
				if(c2.getName().equals(c.getSuperClass())){
					if(checkForSingleton(c2)){
						extendsSingleton = true;
					}
				}
			}
			
			if(checkForSingleton(c) || extendsSingleton){
				this.toReplace.add(c);
			}
		}
		
		for (IClass c : this.toReplace){
			this.model.replaceClass(c, new SingletonDecorator(c));
			IRelation relation = new Relation(c.getName(), c.getName(), RelationType.singleton);
			this.model.addRelation(relation);
		}
		
		
	}
	
	private boolean checkForSingleton(IClass c){
		boolean privateConstructor = false;
		boolean returnsSelf = false;
		boolean isEnum = false;
		boolean hasGetInstance = false;
		
		if(c.getSignature() != null && c.getSignature().contains("Enum")){
			isEnum = true;
		}
		
		for(IMethod m : c.getMethods()){
			if((m.getAccessNumber() == 9 || m.getAccessNumber() == 41) && m.getReturnType().contains(m.getClazz().getName())){
				returnsSelf = true;
			}
			
			if((m.getAccess().equals("-") || m.getAccess().equals("#")) && m.getName().equals("init")){
				privateConstructor = true;
			}
			
			if(m.getAccess().equals("+") && m.getName().equals("getInstance")){
				hasGetInstance = true;
			}
		}
		
		if(instanceRequired && privateConstructor && returnsSelf && !isEnum && hasGetInstance){
			return true;
		}
		
		if(!instanceRequired && privateConstructor && returnsSelf && !isEnum){
			return true;
		}
		return false;
	}
}
