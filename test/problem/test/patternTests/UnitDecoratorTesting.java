package problem.test.patternTests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import problem.asm.DesignParser;
import problem.model.decorators.ComponentDecorator;
import problem.model.decorators.DecoratorDecorator;
import problem.model.detectors.DecoratorDetector;
import problem.model.detectors.IPatternDetector;
import problem.models.api.IClass;
import problem.models.impl.Model;

public class UnitDecoratorTesting {

private DesignParser parser;
	
	@Before
	public void SetUp(){
		parser = new DesignParser();
	}
	
	@Test
	public void testUnitDecorator() throws IOException {
		Model m = Model.getInstance();
		m.clearModel();
		String[] classes = {"problem.test.patternClasses.ConcreteComponentDecorator",
				"problem.test.patternClasses.ComponentInterface",
				"problem.test.patternClasses.ComponentDecorator"};
		parser.visitClasses(classes,m);
		IPatternDetector pd = new DecoratorDetector(m);
		pd.detectPatterns();
		
		for(IClass c: m.getClasses()){
			if(c.getName().equals("ComponentDecorator") ||
				c.getName().equals("ConcreteComponentDecorator")){
				assertTrue(c instanceof DecoratorDecorator);
			}
			
			if(c.getName().equals("ComponentInterface")){
					assertTrue(c instanceof ComponentDecorator);
				}
		}
	}

}
