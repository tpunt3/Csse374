package problem.test.patternTests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import problem.asm.DesignParser;
import problem.model.decorators.AdapteeDecorator;
import problem.model.decorators.AdapterDecorator;
import problem.model.decorators.TargetDecorator;
import problem.model.detectors.AdapterDetector;
import problem.model.detectors.IPatternDetector;
import problem.models.api.IClass;
import problem.models.impl.Model;

public class UnitAdapterTesting {

	private DesignParser parser;

	@Before
	public void SetUp() {
		parser = new DesignParser("\"C:\\Users\\leekf\\Documents\\JUNIOR\\CSSE374\\release\\bin\\dot\"", "\"C:\\Users\\leekf\\Documents\\JUNIOR\\CSSE374\\sdedit-4.2-beta1.exe\"");
		//parser = new DesignParser("\"C:\\Users\\punttj\\Desktop\\csse374\\release\\bin\\dot\"","\"C:\\Users\\punttj\\Desktop\\csse374\\finalProject\\sdedit-4.2-beta1.exe\"");
	}

	@Test
	public void testUnitAdapter() throws IOException {
		Model m = Model.getInstance();
		m.clearModel();
		String[] classes = { "problem.test.patternClasses.AdapteeClass", "problem.test.patternClasses.AdapterClass",
				"problem.test.patternClasses.TargetInterface" };
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
	
	@Test
	public void testFalseUnitAdapter() throws IOException {
		Model m = Model.getInstance();
		m.clearModel();
		String[] classes = { "problem.test.patternClasses.AdapteeClass", "problem.test.patternClasses.FalseAdapterClass",
				"problem.test.patternClasses.TargetInterface" };
		parser.visitClasses(classes, m);
		IPatternDetector pd = new AdapterDetector(m);
		pd.detectPatterns();

		for (IClass c : m.getClasses()) {
			if (c.getName().equals("AdapteeClass")) {
				assertFalse(c instanceof AdapteeDecorator);
			}

			if (c.getName().equals("AdapterClass")) {
				assertFalse(c instanceof AdapterDecorator);
			}
			if (c.getName().equals("TargetInterface")) {
				assertFalse(c instanceof TargetDecorator);
			}
		}
	}

}
