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
		//parser = new DesignParser("\"C:\\Users\\punttj\\Desktop\\csse374\\release\\bin\\dot\"","\"C:\\Users\\punttj\\Desktop\\csse374\\finalProject\\sdedit-4.2-beta1.exe\"");
		parser = new DesignParser("\"C:\\Users\\leekf\\Documents\\JUNIOR\\CSSE374\\release\\bin\\dot\"", "\"C:\\Users\\leekf\\Documents\\JUNIOR\\CSSE374\\sdedit-4.2-beta1.exe\"");
	}
	
	@Test
	public void testUnitDecorator() throws IOException {
		Model m = Model.getInstance();
		m.clearModel();
		String[] classes = {"problem.test.patternClasses.ConcreteComponentDecorator",
				"problem.test.patternClasses.ComponentInterface",
				"problem.test.patternClasses.ComponentDecorator"};
		m=parser.visitClasses(classes);
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
