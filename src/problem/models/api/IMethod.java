package problem.models.api;

public interface IMethod extends IElement{
	public String getAccess();
	public String getName();
	public String getArgs();
	public String getReturnType();
}
