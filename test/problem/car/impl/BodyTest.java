package problem.car.impl;

import static org.junit.Assert.*;

import org.junit.Test;

public class BodyTest {
	private final Body body;
	private final String type;
	private final String material; 

	public BodyTest() {
		type = "coupe";
		material = "alumunium";
		this.body = new Body(type, material);
	}
	
	@Test
	public void testBody() {
		assertNotNull(this.body);
	}

	@Test
	public void testGetType() {
		assertEquals(this.type, this.body.getType());
	}

	@Test
	public void testGetMaterial() {
		assertEquals(this.material, this.body.getMaterial());
	}
}
