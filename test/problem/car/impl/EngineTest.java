package problem.car.impl;

import static org.junit.Assert.*;

import org.junit.Test;

import problem.car.api.IEngine;

public class EngineTest {
	private final int cylinder;
	private final float capacity;
	private final IEngine engine;
	
	public EngineTest() {
		this.cylinder = 3;
		this.capacity = 2;
		this.engine = new Engine(this.cylinder, this.capacity);
	}

	@Test
	public void testEngine() {
		assertNotNull(this.engine);
	}

	@Test
	public void testGetCylinder() {
		assertEquals(this.cylinder, this.engine.getCylinder());
	}

	@Test
	public void testGetCapacity() {
		assertEquals(this.capacity, this.engine.getCapacity(), 0.000001);
	}
}
