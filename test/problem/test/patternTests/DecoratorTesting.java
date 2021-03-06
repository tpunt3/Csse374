package problem.test.patternTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

public class DecoratorTesting {
	
private DesignParser parser;
	
	@Before
	public void SetUp(){
		//parser = new DesignParser("\"C:\\Users\\punttj\\Desktop\\csse374\\release\\bin\\dot\"","\"C:\\Users\\punttj\\Desktop\\csse374\\finalProject\\sdedit-4.2-beta1.exe\"");
		parser = new DesignParser("\"C:\\Users\\leekf\\Documents\\JUNIOR\\CSSE374\\release\\bin\\dot\"", "\"C:\\Users\\leekf\\Documents\\JUNIOR\\CSSE374\\sdedit-4.2-beta1.exe\"");
	}

	@Test
	public void testOuputStream() throws IOException {
		Model m = Model.getInstance();
		m.clearModel();
		String[] classes = {"java.lang.Object","java.io.OutputStreamWriter","java.io.Writer"};
		m=parser.visitClasses(classes);
		IPatternDetector pd = new DecoratorDetector(m);
		pd.detectPatterns();
		
		for(IClass c: m.getClasses()){
			if(c.getName().equals("OutputStreamWriter")){
				assertFalse(c instanceof DecoratorDecorator);
			}
		}
	}
	
	@Test
	public void testInputStream() throws IOException {
		Model m = Model.getInstance();
		m.clearModel();
		String[] classes = {"java.lang.Object","java.io.InputStreamReader","java.io.Reader"};
		m=parser.visitClasses(classes);
		IPatternDetector pd = new DecoratorDetector(m);
		pd.detectPatterns();
		
		for(IClass c: m.getClasses()){
			if(c.getName().equals("InputStreamReader")){
				assertFalse(c instanceof DecoratorDecorator);
			}
		}
	}

	@Test 
	public void testStarbuzz() throws IOException {
		Model m = Model.getInstance();
		m.clearModel();
		String[] classes = {"headfirst.decorator.starbuzz.Beverage", 
				"headfirst.decorator.starbuzz.CondimentDecorator",
				"headfirst.decorator.starbuzz.DarkRoast",
				"headfirst.decorator.starbuzz.Decaf",
				"headfirst.decorator.starbuzz.Espresso",
				"headfirst.decorator.starbuzz.HouseBlend",
				"headfirst.decorator.starbuzz.Milk", 
				"headfirst.decorator.starbuzz.Mocha",
				"headfirst.decorator.starbuzz.Soy",
				"headfirst.decorator.starbuzz.StarbuzzCoffee",
				"headfirst.decorator.starbuzz.Whip"};
		m=parser.visitClasses(classes);
		IPatternDetector pd = new DecoratorDetector(m);
		pd.detectPatterns();
		
		for(IClass c: m.getClasses()){
			if(c.getName().equals("CondimentDecorator") || 
					c.getName().equals("Mocha") ||
					c.getName().equals("Milk") ||
					c.getName().equals("Soy") ||
					c.getName().equals("Whip")){
				assertTrue(c instanceof DecoratorDecorator);
			}
			
			if(c.getName().equals("Beverage")){
				assertTrue(c instanceof ComponentDecorator);
			}
		}
	}
	
	@Test
	public void testClassDecorator() throws IOException{
		Model m = Model.getInstance();
		m.clearModel();
		String[] classes = {"problem.model.decorators.AdapteeDecorator",
				"problem.model.decorators.AdapterDecorator",
				"problem.model.decorators.ClassDecorator",
				"problem.model.decorators.ComponentDecorator",
				"problem.model.decorators.DecoratorDecorator",
				"problem.model.decorators.SingletonDecorator",
				"problem.model.decorators.TargetDecorator",
				"problem.models.api.IClass"};
		
		m=parser.visitClasses(classes);
		IPatternDetector pd = new DecoratorDetector(m);
		pd.detectPatterns();
		
		for(IClass c: m.getClasses()){
			if(c.getName().equals("AdapteeDecorator") || 
					c.getName().equals("AdapterDecorator") ||
					c.getName().equals("ClassDecorator") ||
					c.getName().equals("ComponentDecorator") ||
					c.getName().equals("DecoratorDecorator") ||
					c.getName().equals("SingletonDecorator") ||
					c.getName().equals("TargetDecorator")){
				assertTrue(c instanceof DecoratorDecorator);
			}
			
			if(c.getName().equals("IClass")){
				assertTrue(c instanceof ComponentDecorator);
			}
		}
	}
	
}
