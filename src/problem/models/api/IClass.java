package problem.models.api;

import java.util.Collection;

public interface IClass extends IElement {
	public String getName();
	public Collection<IMethod> getMethods();
	public Collection<IField> getFields();
	public void addMethod(IMethod m);
	public void addField(IField f);
}
