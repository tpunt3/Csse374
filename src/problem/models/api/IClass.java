package problem.models.api;

import java.util.ArrayList;
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
	public String getSignature();
	public String getSuperClass();
	public ArrayList<String> getInterfaceList();
}
