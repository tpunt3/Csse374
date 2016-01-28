package problem.model.detectors;

import java.util.ArrayList;

import problem.models.api.IClass;
import problem.models.api.IMethod;
import problem.models.api.IModel;
import problem.models.impl.SingletonDecorator;

public class SingletonDetector implements IPatternDetector {
	private IModel model;
	private ArrayList<IClass> toReplace;
	
	public SingletonDetector(IModel model) {
		this.model = model;
		this.toReplace = new ArrayList<IClass>();
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
		}
	}
	
	private boolean checkForSingleton(IClass c){
		boolean privateConstructor = false;
		boolean returnsSelf = false;
		boolean isEnum = false;
		
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
		}
		
		if(privateConstructor && returnsSelf && !isEnum){
			return true;
		}
		return false;
	}
}
