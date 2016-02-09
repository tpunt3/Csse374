package problem.test.patternTests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import problem.asm.DesignParser;
import problem.model.decorators.AdapteeDecorator;
import problem.model.decorators.AdapterDecorator;
import problem.model.decorators.DecoratorDecorator;
import problem.model.decorators.TargetDecorator;
import problem.model.detectors.AdapterDetector;
import problem.model.detectors.DecoratorDetector;
import problem.model.detectors.IPatternDetector;
import problem.models.api.IClass;
import problem.models.impl.Model;

public class AdapterTesting {

	private DesignParser parser;
	
	@Before
	public void setUp() throws Exception {
		parser = new DesignParser("\"C:\\Users\\punttj\\Desktop\\csse374\\release\\bin\\dot\"","\"C:\\Users\\punttj\\Desktop\\csse374\\finalProject\\sdedit-4.2-beta1.exe\"");
		
	}

	@Test
	public void testIteratorToEnumeratorAdapter() throws IOException {
		Model m = Model.getInstance();
		m.clearModel();
		String[] classes = {"problem.client.App","problem.client.IteratorToEnumerationAdapter","problem.lib.LinearTransformer", "java.util.Iterator", "java.util.Enumeration"};
		parser.visitClasses(classes,m);
		IPatternDetector pd = new AdapterDetector(m);
		pd.detectPatterns();
		
		for(IClass c: m.getClasses()){
			if(c.getName().equals("IteratorToEnumerationAdapter")){
				assertTrue(c instanceof AdapterDecorator);
			} else if(c.getName().equals("Iterator")){
				assertTrue(c instanceof AdapteeDecorator);
			} else if(c.getName().equals("Enumeration")){
				assertTrue(c instanceof TargetDecorator);
			} else{
				assertFalse(c instanceof TargetDecorator);
				assertFalse(c instanceof AdapterDecorator);
				assertFalse(c instanceof AdapteeDecorator);
			}
		}
	}
	
	@Test
	public void testMouseAdapter() throws IOException {
		Model m = Model.getInstance();
		m.clearModel();
		String[] classes = {"java.awt.event.MouseAdapter", "java.awt.event.MouseListener","java.awt.event.MouseWheelListener", "java.awt.event.MouseMotionListener"};
		parser.visitClasses(classes,m);
		IPatternDetector pd = new AdapterDetector(m);
		pd.detectPatterns();
		
		for(IClass c: m.getClasses()){
			assertFalse(c instanceof AdapteeDecorator);
			assertFalse(c instanceof AdapterDecorator);
			assertFalse(c instanceof TargetDecorator);
		}
	}
}


