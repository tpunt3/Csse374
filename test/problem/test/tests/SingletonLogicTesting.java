package problem.test.tests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import problem.asm.DesignParser;
import problem.asm.DocType;
import problem.model.decorators.PatternType;
import problem.model.detectors.IPatternDetector;
import problem.model.detectors.SingletonDetector;
import problem.models.api.IClass;
import problem.models.impl.Model;
import problem.models.impl.SingletonDecorator;

public class SingletonLogicTesting {
	
	private DesignParser parser;
	
	@Before
	public void SetUp(){
		parser = new DesignParser();
	}

	@Test
	public void testLazySingleton() throws IOException {
		Model m = Model.getInstance();
		String[] classes = {"problem.test.classes.LazySingleton", "problem.test.classes.EmptyClass"};
		parser.visitClasses(classes,m);
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
		String[] classes = {"problem.test.classes.EagerSingleton", "problem.test.classes.FieldClass"};
		parser.visitClasses(classes,m);
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
		String[] classes = {"problem.test.classes.EagerSingleton", "problem.test.classes.AlmostSingleton"};
		parser.visitClasses(classes,m);
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
