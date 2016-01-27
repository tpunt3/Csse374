package problem.model.decorators;

import problem.models.api.IClass;
import problem.models.api.IMethod;
import problem.models.api.IModel;
import problem.models.impl.SingletonDecorator;

public class PatternDetector implements IPatternDetector {
	private IModel model;
	
	public PatternDetector(IModel model) {
		this.model = model;
	}
	
	@Override
	public void detectPatterns(){
		for(IClass c : model.getClasses()){
			boolean privateConstructor = false;
			boolean returnsSelf = false;
			
			for(IMethod m : c.getMethods()){
				if(m.getAccessNumber() == 9 && m.getReturnType().contains(m.getClazz().getName())){
					returnsSelf = true;
				}
				
				if(m.getAccess().equals("-") && m.getName().equals("init")){
					privateConstructor = true;
				}
			}
			
			if(privateConstructor && returnsSelf){
				c = new SingletonDecorator(c);
				
			}
		}
	}

}
