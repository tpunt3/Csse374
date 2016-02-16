package problem.test.patternTests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import problem.asm.DesignParser;
import problem.model.decorators.SingletonDecorator;
import problem.model.detectors.IPatternDetector;
import problem.model.detectors.SingletonDetector;
import problem.models.api.IClass;
import problem.models.impl.Model;

public class Lab4SingletonTests {

	private DesignParser parser;

	@Before
	public void setUp() throws Exception {
		parser = new DesignParser("\"C:\\Users\\punttj\\Desktop\\csse374\\release\\bin\\dot\"","\"C:\\Users\\punttj\\Desktop\\csse374\\finalProject\\sdedit-4.2-beta1.exe\"");
		//parser = new DesignParser("\"C:\\Users\\leekf\\Documents\\JUNIOR\\CSSE374\\release\\bin\\dot\"", "\"C:\\Users\\leekf\\Documents\\JUNIOR\\CSSE374\\sdedit-4.2-beta1.exe\"");
		Model m = Model.getInstance();

		m.clearModel();
	}

	@Test
	public void testSingletonChocolate() throws IOException {
		Model m = Model.getInstance();
		String[] classes = { "headfirst.singleton.chocolate.ChocolateBoiler",
				"headfirst.singleton.chocolate.ChocolateController" };
		m=parser.visitClasses(classes);
		IPatternDetector pd = new SingletonDetector(m);
		pd.detectPatterns();

		for (IClass c : m.getClasses()) {
			if (c.getName().contains("ChocolateBoiler")) {
				assertTrue(c instanceof SingletonDecorator);
			} else {
				assertFalse(c instanceof SingletonDecorator);
			}
		}
		m.clearModel();
	}

	@Test
	public void testSingletonClassic() throws IOException {
		Model m = Model.getInstance();
		String[] classes = { "headfirst.singleton.classic.Singleton" };
		m=parser.visitClasses(classes);
		IPatternDetector pd = new SingletonDetector(m);
		pd.detectPatterns();

		for (IClass c : m.getClasses()) {
			if (c.getName().contains("Singleton")) {
				assertTrue(c instanceof SingletonDecorator);
			}
		}
		m.clearModel();
	}
	
	@Test
	public void testSingletonDcl() throws IOException {
		Model m = Model.getInstance();
		String[] classes = { "headfirst.singleton.dcl.Singleton", "headfirst.singleton.dcl.SingletonClient" };
		m=parser.visitClasses(classes);
		IPatternDetector pd = new SingletonDetector(m);
		pd.detectPatterns();

		for (IClass c : m.getClasses()) {
			if (c.getName().equals("Singleton")) {
				assertTrue(c instanceof SingletonDecorator);
			}else{
				assertFalse(c instanceof SingletonDecorator);
			}
		}
		m.clearModel();
	}
	
	@Test
	public void testSingletonStat() throws IOException {
		Model m = Model.getInstance();
		String[] classes = { "headfirst.singleton.stat.Singleton", "headfirst.singleton.stat.SingletonClient" };
		m=parser.visitClasses(classes);
		IPatternDetector pd = new SingletonDetector(m);
		pd.detectPatterns();

		for (IClass c : m.getClasses()) {
			if (c.getName().equals("Singleton")) {
				assertTrue(c instanceof SingletonDecorator);
			}else{
				assertFalse(c instanceof SingletonDecorator);
			}
		}
		m.clearModel();
	}
	
	@Test
	public void testSingletonSubClass() throws IOException {
		Model m = Model.getInstance();
		String[] classes = { "headfirst.singleton.subclass.Singleton", "headfirst.singleton.subclass.CoolerSingleton","headfirst.singleton.subclass.HotterSingleton","headfirst.singleton.subclass.SingletonTestDrive" };
		m=parser.visitClasses(classes);
		IPatternDetector pd = new SingletonDetector(m);
		pd.detectPatterns();

		for (IClass c : m.getClasses()) {
			if (!(c.getName().equals("SingletonTestDrive"))) {
				assertTrue(c instanceof SingletonDecorator);
			}else{
				assertFalse(c instanceof SingletonDecorator);
			}
		}
		m.clearModel();
	}
	
	@Test
	public void testSingletonThreadSafe() throws IOException {
		Model m = Model.getInstance();
		String[] classes = { "headfirst.singleton.threadsafe.Singleton"};
		m=parser.visitClasses(classes);
		IPatternDetector pd = new SingletonDetector(m);
		pd.detectPatterns();

		for (IClass c : m.getClasses()) {
			if (c.getName().contains("Singleton")) {
				assertTrue(c instanceof SingletonDecorator);
			}
		}
		m.clearModel();
	}


}
