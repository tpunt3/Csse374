package problem.test.patternTests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import problem.asm.DesignParser;
import problem.asm.DocType;
import problem.model.decorators.SingletonDecorator;
import problem.model.detectors.IPatternDetector;
import problem.model.detectors.SingletonDetector;
import problem.models.api.IClass;
import problem.models.impl.Model;

public class SingletonLogicTesting {
	
	private DesignParser parser;
	
	@Before
	public void SetUp(){
		//parser = new DesignParser("\"C:\\Users\\punttj\\Desktop\\csse374\\release\\bin\\dot\"","\"C:\\Users\\punttj\\Desktop\\csse374\\finalProject\\sdedit-4.2-beta1.exe\"");
		parser = new DesignParser("\"C:\\Users\\leekf\\Documents\\JUNIOR\\CSSE374\\release\\bin\\dot\"", "\"C:\\Users\\leekf\\Documents\\JUNIOR\\CSSE374\\sdedit-4.2-beta1.exe\"");
	}

	@Test
	public void testLazySingleton() throws IOException {
		Model m = Model.getInstance();
		String[] classes = {"problem.test.patternClasses.LazySingleton", "problem.test.classes.EmptyClass"};
		m=parser.visitClasses(classes);
		IPatternDetector pd = new SingletonDetector(m);
		pd.detectPatterns();
		
		for(IClass c: m.getClasses()){
			if(c.getName().equals("LazySingleton")){
				assertTrue(c instanceof SingletonDecorator);
			}else{
				assertFalse(c instanceof SingletonDecorator);
			}
		}
	}
	
	@Test
	public void testEagerSingleton() throws IOException {
		Model m = Model.getInstance();
		m.clearModel();
		String[] classes = {"problem.test.patternClasses.EagerSingleton", "problem.test.classes.FieldClass"};
		m=parser.visitClasses(classes);
		IPatternDetector pd = new SingletonDetector(m);
		pd.detectPatterns();
		
		for(IClass c: m.getClasses()){
			if(c.getName().equals("EagerSingleton") || c.getName().equals("LazySingleton")){
				assertTrue(c instanceof SingletonDecorator);
			}else{
				assertFalse(c instanceof SingletonDecorator);
			}
		}
	}

	@Test
	public void testAlmostSingleton() throws IOException {
		Model m = Model.getInstance();
		m.clearModel();
		String[] classes = {"problem.test.patternClasses.EagerSingleton", "problem.test.patternClasses.AlmostSingleton"};
		m=parser.visitClasses(classes);
		IPatternDetector pd = new SingletonDetector(m);
		pd.detectPatterns();
		
		for(IClass c: m.getClasses()){
			if(c.getName().equals("EagerSingleton") || c.getName().equals("LazySingleton")){
				assertTrue(c instanceof SingletonDecorator);
			}else{
				assertFalse(c instanceof SingletonDecorator);
			}
		}
	}

}
