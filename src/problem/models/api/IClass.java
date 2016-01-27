package problem.models.api;

import java.util.Collection;

import problem.model.decorators.PatternType;
import problem.model.visitor.ITraverser;

public interface IClass extends ITraverser {
	public String getName();
	public Collection<IMethod> getMethods();
	public Collection<IField> getFields();
	public void addMethod(IMethod m);
	public void addField(IField f);
	public boolean getIsClass();
	public boolean isSingleton();
	public void addPattern(PatternType p);
	public Collection<PatternType> getPatterns();
}
