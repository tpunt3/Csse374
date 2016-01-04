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
		
		String gv = "digraph model{rankdir = BT;EmptyClass [shape=\"record\",label = \"{EmptyClass| + init() : void\\"+"l}\"];//writing relations between classes nowEmptyClass -> Object [arrowhead = \"empty\"];}";
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
		
		String gv = "digraph model{rankdir = BT;FieldClass [shape=\"record\",label = \"{FieldClass| + testString: String\\"+"l- testInt: int\\"+"l# testBoolean: boolean\\"+"l|+ init() : void\\"+"l}\"];//writing relations between classes nowFieldClass -> Object [arrowhead = \"empty\"];}";
		
		assertEquals(gv, file); 
		
		buffer.close();
		reader.close();
	}

	@Test
	public final void testClassWithMethod() throws IOException {
		String[] classes = { "problem.test.classes.MethodClass" };
		parser.parse(classes);
		FileReader reader = new FileReader("input_output/test.gv");
		BufferedReader buffer = new BufferedReader(reader);
		

		String file = "";
		String line = "";
		while ((line = buffer.readLine()) != null) {
			file += line;
		}
		
		String gv = "digraph model{rankdir = BT;MethodClass [shape=\"record\",label = \"{MethodClass| + init() : void\\"+"l+ doStuff() : void\\"+"l+ doStuffWithArgs(String; int) : void\\"+"l}\"];//writing relations between classes nowMethodClass -> Object [arrowhead = \"empty\"];}";
		
		assertEquals(gv, file); 
		
		buffer.close();
		reader.close();
	}

	@Test
	public final void testClassWithFieldAndMethod() throws IOException {
		String[] classes = { "problem.test.classes.FieldAndMethodClass" };
		parser.parse(classes);
		FileReader reader = new FileReader("input_output/test.gv");
		BufferedReader buffer = new BufferedReader(reader);

		String file = "";
		String line = "";
		while ((line = buffer.readLine()) != null) {
			file += line;
		}
		
		String gv = "digraph model{rankdir = BT;FieldAndMethodClass [shape=\"record\",label = \"{FieldAndMethodClass| + testString: String\\"+"l- testInt: int\\"+"l# testBoolean: boolean\\"+"l|+ init() : void\\"+"l+ doStuff() : void\\"+"l+ doStuffWithArgs(String; int) : void\\"+"l}\"];//writing relations between classes nowFieldAndMethodClass -> Object [arrowhead = \"empty\"];}";
		
		assertEquals(gv, file); 
		
		buffer.close();
		reader.close();
	}

	@Test
	public final void testClassWithSuperClass() throws IOException {
		String[] classes = { "problem.test.classes.ClassWithSuperClass" };
		parser.parse(classes);
		FileReader reader = new FileReader("input_output/test.gv");
		BufferedReader buffer = new BufferedReader(reader);

		String file = "";
		String line = "";
		while ((line = buffer.readLine()) != null) {
			file += line;
		}
		
		String gv = "digraph model{rankdir = BT;ClassWithSuperClass [shape=\"record\",label = \"{ClassWithSuperClass| + init() : void\\"+"l}\"];//writing relations between classes nowClassWithSuperClass -> EmptyClass [arrowhead = \"empty\"];}";
		
		assertEquals(gv, file); 
		
		buffer.close();
		reader.close();
	}

	@Test
	public final void testClassWithInterface() throws IOException {
		String[] classes = { "problem.test.classes.ClassWithInterface" };
		parser.parse(classes);
		FileReader reader = new FileReader("input_output/test.gv");
		BufferedReader buffer = new BufferedReader(reader);

		String file = "";
		String line = "";
		while ((line = buffer.readLine()) != null) {
			file += line;
		}
		
		String gv = "digraph model{rankdir = BT;ClassWithInterface [shape=\"record\",label = \"{ClassWithInterface| + init() : void\\"+"l}\"];//writing relations between classes nowClassWithInterface -> Object [arrowhead = \"empty\"];ClassWithInterface -> IInterface [arrowhead = \"empty\", style = \"dashed\"];}";
	
		assertEquals(gv, file); 
		
		buffer.close();
		reader.close();
	}

	@Test
	public final void testInterfaceWithSuperClass() throws IOException {
		String[] classes = { "problem.test.classes.IInterfaceWithSuperClass" };
		parser.parse(classes);
		FileReader reader = new FileReader("input_output/test.gv");
		BufferedReader buffer = new BufferedReader(reader);

		String file = "";
		String line = "";
		while ((line = buffer.readLine()) != null) {
			file += line;
		}
		
		String gv = "digraph model{rankdir = BT;IInterfaceWithSuperClass [shape=\"record\",label = \"{\\"+"<\\"+"<interface\\"+">\\"+">\\nIInterfaceWithSuperClass| }\"];//writing relations between classes nowIInterfaceWithSuperClass -> IInterface [arrowhead = \"empty\"];}";
	
		assertEquals(gv, file); 
		
		buffer.close();
		reader.close();
	}

	@Test
	public final void testClassWithEverything() throws IOException {
		String[] classes = { "problem.test.classes.ClassWithEverything" };
		parser.parse(classes);
		FileReader reader = new FileReader("input_output/test.gv");
		BufferedReader buffer = new BufferedReader(reader);

		String file = "";
		String line = "";
		while ((line = buffer.readLine()) != null) {
			file += line;
		}
		
		String gv = "digraph model{rankdir = BT;ClassWithEverything [shape=\"record\",label = \"{ClassWithEverything| - name: String\\"+"l|+ init() : void\\"+"l+ setName(String) : void\\"+"l+ getName() : java.lang.String\\"+"l}\"];//writing relations between classes nowClassWithEverything -> EmptyClass [arrowhead = \"empty\"];ClassWithEverything -> IInterface [arrowhead = \"empty\", style = \"dashed\"];}";
	
		assertEquals(gv, file); 
		
		buffer.close();
		reader.close();
	}

}
