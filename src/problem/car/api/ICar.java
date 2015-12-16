package problem.car.api;

import java.util.Collection;

public interface ICar {
	public String getVIN();
	public String getMake();
	public String getModel();
	public Collection<ICarPart> getParts(); 
}
