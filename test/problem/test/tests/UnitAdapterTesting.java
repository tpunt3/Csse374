package problem.test.tests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import problem.asm.DesignParser;
import problem.model.decorators.AdapteeDecorator;
import problem.model.decorators.AdapterDecorator;
import problem.model.decorators.TargetDecorator;
import problem.model.detectors.AdapterDetector;
import problem.model.detectors.DecoratorDetector;
import problem.model.detectors.IPatternDetector;
import problem.models.api.IClass;
import problem.models.impl.Model;

public class UnitAdapterTesting {

	private DesignParser parser;

	@Before
	public void SetUp() {
		parser = new DesignParser();
	}

	@Test
	public void testUnitAdapter() throws IOException {
		Model m = Model.getInstance();
		m.clearModel();
		String[] classes = { "problem.test.classes.AdapteeClass", "problem.test.classes.AdapterClass",
				"problem.test.classes.TargetInterface" };
		parser.visitClasses(classes, m);
		IPatternDetector pd = new AdapterDetector(m);
		pd.detectPatterns();

		for (IClass c : m.getClasses()) {
			if (c.getName().equals("AdapteeClass")) {
				assertTrue(c instanceof AdapteeDecorator);
			}

			if (c.getName().equals("AdapterClass")) {
				assertTrue(c instanceof AdapterDecorator);
			}
			if (c.getName().equals("TargetInterface")) {
				assertTrue(c instanceof TargetDecorator);
			}
		}
	}

}
