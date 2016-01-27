package problem.test.tests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import problem.asm.DesignParser;
import problem.asm.DocType;
import problem.model.decorators.IPatternDetector;
import problem.model.decorators.PatternDetector;
import problem.model.decorators.PatternType;
import problem.models.api.IClass;
import problem.models.impl.Model;

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
		IPatternDetector pd = new PatternDetector(m);
		pd.detectPatterns();
		
		for(IClass c: m.getClasses()){
			if(c.getName().equals("LazySingleton")){
				assertTrue(c.getPatterns().contains(PatternType.Singleton));
			}else{
				assertFalse(c.getPatterns().contains(PatternType.Singleton));
			}
		}
	}
	
	@Test
	public void testEagerSingleton() throws IOException {
		Model m = Model.getInstance();
		String[] classes = {"problem.test.classes.EagerSingleton", "problem.test.classes.FieldClass"};
		parser.visitClasses(classes,m);
		IPatternDetector pd = new PatternDetector(m);
		pd.detectPatterns();
		
		for(IClass c: m.getClasses()){
			if(c.getName().equals("EagerSingleton") || c.getName().equals("LazySingleton")){
				System.out.println(c.getName());
				assertTrue(c.getPatterns().contains(PatternType.Singleton));
			}else{
				assertFalse(c.getPatterns().contains(PatternType.Singleton));
			}
		}
	}

	@Test
	public void testAlmostSingleton() throws IOException {
		Model m = Model.getInstance();
		String[] classes = {"problem.test.classes.EagerSingleton", "problem.test.classes.AlmostSingleton"};
		parser.visitClasses(classes,m);
		IPatternDetector pd = new PatternDetector(m);
		pd.detectPatterns();
		
		for(IClass c: m.getClasses()){
			if(c.getName().equals("EagerSingleton") || c.getName().equals("LazySingleton")){
				assertTrue(c.getPatterns().contains(PatternType.Singleton));
			}else{
				System.out.println(c.getName()+c.isSingleton());
				assertFalse(c.getPatterns().contains(PatternType.Singleton));
			}
		}
	}

}
