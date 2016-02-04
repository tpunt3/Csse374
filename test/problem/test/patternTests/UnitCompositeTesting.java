package problem.test.patternTests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import problem.asm.DesignParser;
import problem.model.detectors.AdapterDetector;
import problem.model.detectors.IPatternDetector;
import problem.models.impl.Model;

public class UnitCompositeTesting {

	private DesignParser parser;

	@Before
	public void SetUp() {
		parser = new DesignParser();
	}


	@Test
	public void testDummyComposites() throws IOException {
		Model m = Model.getInstance();
		m.clearModel();
		String[] classes = { "problem.test.patternClasses.Leaf", "problem.test.patternClasses.MyCollectionComposite",
				"problem.test.patternClasses.MyComposite","problem.test.patternClasses.ICompositeComponent"};
		parser.visitClasses(classes, m);
		IPatternDetector pd = new AdapterDetector(m);
		pd.detectPatterns();
	}

}
