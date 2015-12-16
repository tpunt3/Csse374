package problem.car.impl;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.Collection;
import java.util.HashSet;

import org.junit.Test;

import problem.car.api.ICar;
import problem.car.api.ICarPart;


public class CarTest {
	private final String vin;
	private final String make;
	private final String model;
	private final Collection<ICarPart> parts;
	private final ICar car;
	
	public CarTest() {
		vin = "123";
		make = "abc";
		model = "def";
		parts = new HashSet<>();
		parts.add(new Body("abc", "abc"));
		parts.add(new Body("pqr", "pqr"));

		car = new Car(vin, make, model, parts);
	}

	@Test
	public void testCar() {
		assertNotNull(car);
	}

	@Test
	public void testGetVIN() {
		assertEquals(this.vin, car.getVIN());
	}

	@Test
	public void testGetMake() {
		assertEquals(this.make, car.getMake());
	}

	@Test
	public void testGetModel() {
		assertEquals(this.model, car.getModel());
	}

	@Test
	public void testGetParts() {
		assertThat(car.getParts(), hasItems(this.parts.toArray(new ICarPart[0])));
	}
}
