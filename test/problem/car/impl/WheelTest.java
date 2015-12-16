package problem.car.impl;

import static org.junit.Assert.*;

import org.junit.Test;

import problem.car.api.IWheel;

public class WheelTest {
	private final String vendor;
	private final float radius;
	private final float width;
	private final IWheel wheel;
	
	public WheelTest() {
		this.vendor = "abc";
		this.radius = 7.5f;
		this.width = 9.1f;
		this.wheel = new Wheel(this.vendor, this.radius, this.width);
	}

	@Test
	public void testWheel() {
		assertNotNull(this.wheel);
	}

	@Test
	public void testGetVendor() {
		assertEquals(this.vendor, this.wheel.getVendor());
	}

	@Test
	public void testGetRadius() {
		assertEquals(this.radius, this.wheel.getRadius(), 0.00001);
	}

	@Test
	public void testGetWidth() {
		assertEquals(this.width, this.wheel.getWidth(), 0.00001);
	}
}
