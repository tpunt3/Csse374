package problem.test.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import problem.asm.DesignParser;
import problem.model.decorators.DecoratorDecorator;
import problem.model.detectors.DecoratorDetector;
import problem.model.detectors.IPatternDetector;
import problem.models.api.IClass;
import problem.models.impl.Model;

public class DecoratorTesting {
	
private DesignParser parser;
	
	@Before
	public void SetUp(){
		parser = new DesignParser();
	}

	@Test
	public void testOuputStream() throws IOException {
		Model m = Model.getInstance();
		m.clearModel();
		String[] classes = {"java.lang.Object","java.io.OutputStreamWriter","java.io.Writer"};
		parser.visitClasses(classes,m);
		IPatternDetector pd = new DecoratorDetector(m);
		pd.detectPatterns();
		
		for(IClass c: m.getClasses()){
			if(c.getName().equals("OutputStreamWriter")){
				assertTrue(c instanceof DecoratorDecorator);
			}
		}
	}
	
	@Test
	public void testInputStream() throws IOException {
		Model m = Model.getInstance();
		m.clearModel();
		String[] classes = {"java.lang.Object","java.io.InputStreamReader","java.io.Reader"};
		parser.visitClasses(classes,m);
		IPatternDetector pd = new DecoratorDetector(m);
		pd.detectPatterns();
		
		for(IClass c: m.getClasses()){
			if(c.getName().equals("InputStreamReader")){
				assertTrue(c instanceof DecoratorDecorator);
			}
		}
	}

}
