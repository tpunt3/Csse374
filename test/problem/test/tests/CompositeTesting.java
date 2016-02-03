package problem.test.tests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import problem.asm.DesignParser;
import problem.model.decorators.ComponentDecorator;
import problem.model.decorators.CompositeComponentDecorator;
import problem.model.decorators.CompositeDecorator;
import problem.model.decorators.LeafDecorator;
import problem.model.detectors.AdapterDetector;
import problem.model.detectors.CompositeDetector;
import problem.model.detectors.IPatternDetector;
import problem.models.api.IClass;
import problem.models.impl.Model;

public class CompositeTesting {

	private DesignParser parser;

	@Before
	public void setUp() throws Exception {
		parser = new DesignParser();

	}

	@Test
	public void testMenu() throws IOException {
		Model m = Model.getInstance();
		m.clearModel();
		String[] classes = { "headfirst.composite.menu.Menu", "headfirst.composite.menu.MenuComponent",
				"headfirst.composite.menu.MenuItem", "headfirst.composite.menu.MenuTestDrive",
				"headfirst.composite.menu.Waitress" };
		parser.visitClasses(classes, m);
		IPatternDetector pd = new CompositeDetector(m);
		pd.detectPatterns();

		for (IClass c : m.getClasses()) {
			if (c.getName().equals("MenuComponent")) {
				assertTrue(c instanceof CompositeComponentDecorator);
			} else if (c.getName().equals("MenuItem")) {
				assertTrue(c instanceof LeafDecorator);
			} else if (c.getName().equals("Menu")) {
				assertTrue(c instanceof CompositeDecorator);
			} else {
				assertFalse(c instanceof CompositeComponentDecorator);
				assertFalse(c instanceof LeafDecorator);
				assertFalse(c instanceof CompositeDecorator);
			}
		}
	}

	@Test
	public void testSprites() throws IOException {
		Model m = Model.getInstance();
		m.clearModel();
		String[] classes = { "problem.sprites.AbstractSprite", "problem.sprites.CircleSprite",
				"problem.sprites.CompositeSprite", "problem.sprites.CompositeSpriteIterator",
				"problem.sprites.CrystalBall", "problem.sprites.ISprite", "problem.sprites.NullSpriteIterator",
				"problem.sprites.RectangleSprite", "problem.sprites.RectangleTower", "problem.sprites.SpriteFactory" };
		parser.visitClasses(classes, m);
		IPatternDetector pd = new CompositeDetector(m);
		pd.detectPatterns();

		for (IClass c : m.getClasses()) {
			if (c.getName().equals("AbstractSprite") || c.getName().equals("ISprite")) {
				assertTrue(c instanceof CompositeComponentDecorator);
			} else if (c.getName().equals("CircleSprite") ||c.getName().equals("RectangleSprite")) {
				assertTrue(c instanceof LeafDecorator);
			} else if (c.getName().equals("CrystalBall") || c.getName().equals("RectangleTower") || c.getName().equals("CompositeSprite")) {
				assertTrue(c instanceof CompositeDecorator);
			} else {
				assertFalse(c instanceof CompositeComponentDecorator);
				assertFalse(c instanceof LeafDecorator);
				assertFalse(c instanceof CompositeDecorator);
			}
		}
	}

}
