package problem.model.patternvisitor;

import java.util.HashMap;
import java.util.Map;

import problem.model.visitor.ITraverser;
import problem.model.visitor.IVisitMethod;
import problem.model.visitor.LookupKey;
import problem.model.visitor.VisitType;

public class PatternVisitor implements IPatternVisitor {
	Map<LookupKey, IPatternVisitMethod> keyToCommandMap;
	
	public PatternVisitor() {
		this.keyToCommandMap = new HashMap<>();
	}
	
	@Override
	public void singletonVisit(ITraverser t) {
		this.doVisit(VisitType.SingletonPattern, t);

	}
	
	private void doVisit(VisitType vType, ITraverser t){
		LookupKey key = new LookupKey(vType, t.getClass());
		IPatternVisitMethod m = this.keyToCommandMap.get(key);
		if(m != null) {
			m.execute(t);
		}
	}

	@Override
	public void addVisit(VisitType vType, Class clazz, IPatternVisitMethod m) {
		LookupKey key = new LookupKey(vType, clazz);
		this.keyToCommandMap.put(key, m);
	}

	@Override
	public void removeVisit(VisitType vType, Class clazz) {
		LookupKey key = new LookupKey(vType, clazz);
		this.keyToCommandMap.remove(key);
	}

}
