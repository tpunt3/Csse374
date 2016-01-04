package problem.test.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

import problem.AppLauncher;

public class GVTesting {

	private TestDesignParser parser;

	@Before
	public void setUp() throws Exception {
		parser = new TestDesignParser();
	}

	@Test
	public final void testEmptyClass() throws IOException {
		String[] classes = { "problem.test.classes.EmptyClass" };
		parser.parse(classes);
		FileReader reader = new FileReader("input_output/test.gv");
		BufferedReader buffer = new BufferedReader(reader);

		String file = "";
		String line = "";
		while ((line = buffer.readLine()) != null) {
			file += line;
		}
		System.out.println(file);
		
		String gv = "digraph model{rankdir = BT;EmptyClass [shape=\"record\",label = \"{EmptyClass| + init() : void\\"+"l}\"];//writing relations between classes nowEmptyClass -> Object [arrowhead = \"empty\"];}";
		System.out.println(gv);
		assertEquals(gv, file); 
		
		buffer.close();
		reader.close();
	}

	@Test
	public final void testClassWithField() throws IOException {
		String[] classes = { "problem.test.classes.FieldClass" };
		parser.parse(classes);
		FileReader reader = new FileReader("input_output/test.gv");
		BufferedReader buffer = new BufferedReader(reader);
		

		String file = "";
		String line = "";
		while ((line = buffer.readLine()) != null) {
			file += line;
		}
		System.out.println(file);
		
		String gv = "";
		
		System.out.println(gv);
		assertEquals(gv, file); 
		
		buffer.close();
		reader.close();
	}

	@Test
	public final void testClassWithMethod() throws IOException {
		String[] classes = { "problem.test.classes.EmptyClass" };
		parser.parse(classes);
		FileReader reader = new FileReader("input_output/test.gv");
	}

	@Test
	public final void testClassWithFieldAndMethod() throws IOException {
		String[] classes = { "problem.test.classes.EmptyClass" };
		parser.parse(classes);
		FileReader reader = new FileReader("input_output/test.gv");
	}

	@Test
	public final void testClassWithSuperClass() throws IOException {
		String[] classes = { "problem.test.classes.EmptyClass" };
		parser.parse(classes);
		FileReader reader = new FileReader("input_output/test.gv");
	}

	@Test
	public final void testClassWithInterface() throws IOException {
		String[] classes = { "problem.test.classes.EmptyClass" };
		parser.parse(classes);
		FileReader reader = new FileReader("input_output/test.gv");
	}

	@Test
	public final void testInterfaceWithSuperClass() throws IOException {
		String[] classes = { "problem.test.classes.EmptyClass" };
		parser.parse(classes);
		FileReader reader = new FileReader("input_output/test.gv");
	}

	@Test
	public final void testClassWithEverything() throws IOException {
		String[] classes = { "problem.test.classes.EmptyClass" };
		parser.parse(classes);
		FileReader reader = new FileReader("input_output/test.gv");
	}

}
