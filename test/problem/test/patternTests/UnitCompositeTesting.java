package problem.test.patternTests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import problem.asm.DesignParser;
import problem.model.decorators.AdapteeDecorator;
import problem.model.decorators.AdapterDecorator;
import problem.model.decorators.CompositeComponentDecorator;
import problem.model.decorators.CompositeDecorator;
import problem.model.decorators.LeafDecorator;
import problem.model.decorators.TargetDecorator;
import problem.model.detectors.AdapterDetector;
import problem.model.detectors.CompositeDetector;
import problem.model.detectors.IPatternDetector;
import problem.models.api.IClass;
import problem.models.impl.Model;

public class UnitCompositeTesting {

	private DesignParser parser;

	@Before
	public void SetUp() {
		//parser = new DesignParser("\"C:\\Users\\punttj\\Desktop\\csse374\\release\\bin\\dot\"","\"C:\\Users\\punttj\\Desktop\\csse374\\finalProject\\sdedit-4.2-beta1.exe\"");
		parser = new DesignParser("\"C:\\Users\\leekf\\Documents\\JUNIOR\\CSSE374\\release\\bin\\dot\"", "\"C:\\Users\\leekf\\Documents\\JUNIOR\\CSSE374\\sdedit-4.2-beta1.exe\"");
	}

	@Test
	public void testDummyComposites() throws IOException {
		Model m = Model.getInstance();
		m.clearModel();
		String[] classes = { "problem.test.patternClasses.Leaf", "problem.test.patternClasses.MyComposite",
				"problem.test.patternClasses.CompositeComponent" };
		m=parser.visitClasses(classes);
		IPatternDetector pd = new CompositeDetector(m);
		pd.detectPatterns();
		
		for (IClass c : m.getClasses()) {
			if (c.getName().equals("Leaf")) {
				assertTrue(c instanceof LeafDecorator);
			}

			if (c.getName().equals("MyComposite")) {
				assertTrue(c instanceof CompositeDecorator);
			}
			if (c.getName().equals("ICompositeComponent")) {
				assertTrue(c instanceof CompositeComponentDecorator);
			}
		}
		
	}

	@Test
	public void testCollectionComposites() throws IOException {
		Model m = Model.getInstance();
		m.clearModel();
		String[] classes = { "problem.test.patternClasses.Leaf", "problem.test.patternClasses.MyCollectionComposite",
				"problem.test.patternClasses.CompositeComponent" };
		m=parser.visitClasses(classes);
		IPatternDetector pd = new CompositeDetector(m);
		pd.detectPatterns();
		
		for (IClass c : m.getClasses()) {
			if (c.getName().equals("Leaf")) {
				assertTrue(c instanceof LeafDecorator);
			}

			if (c.getName().equals("MyCollectionComposite")) {
				assertTrue(c instanceof CompositeDecorator);
			}
			if (c.getName().equals("ICompositeComponent")) {
				assertTrue(c instanceof CompositeComponentDecorator);
			}
		}
	}

}
