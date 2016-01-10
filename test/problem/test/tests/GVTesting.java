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

		String gv = "digraph model{rankdir = BT;EmptyClass [shape=\"record\",label = \"{EmptyClass| + init() : void\\"
				+ "l}\"];//writing relations between classes now}";
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

		String gv = "digraph model{rankdir = BT;FieldClass [shape=\"record\",label = \"{FieldClass| + testString: String\\"
				+ "l- testInt: int\\" + "l# testBoolean: boolean\\" + "l|+ init() : void\\"
				+ "l}\"];//writing relations between classes now}";

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

		String gv = "digraph model{rankdir = BT;MethodClass [shape=\"record\",label = \"{MethodClass| + init() : void\\"
				+ "l+ doStuff() : void\\" + "l+ doStuffWithArgs(String; int) : void\\"
				+ "l}\"];//writing relations between classes now}";

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

		String gv = "digraph model{rankdir = BT;FieldAndMethodClass [shape=\"record\",label = \"{FieldAndMethodClass| + testString: String\\"
				+ "l- testInt: int\\" + "l# testBoolean: boolean\\" + "l|+ init() : void\\" + "l+ doStuff() : void\\"
				+ "l+ doStuffWithArgs(String; int) : void\\" + "l}\"];//writing relations between classes now}";

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

		String gv = "digraph model{rankdir = BT;ClassWithSuperClass [shape=\"record\",label = \"{ClassWithSuperClass| + init() : void\\"
				+ "l}\"];//writing relations between classes now}";

		assertEquals(gv, file);

		buffer.close();
		reader.close();
	}

	@Test
	public final void testClassWithInterface() throws IOException {
		String[] classes = { "problem.test.classes.ClassWithInterface", "problem.test.classes.IInterface" };
		parser.parse(classes);
		FileReader reader = new FileReader("input_output/test.gv");
		BufferedReader buffer = new BufferedReader(reader);

		String file = "";
		String line = "";
		while ((line = buffer.readLine()) != null) {
			file += line;
		}

		String gv = "digraph model{rankdir = BT;ClassWithInterface [shape=\"record\",label = \"{ClassWithInterface| + init() : void\\"
				+ "l}\"];IInterface [shape=\"record\",label = \"{\\" + "<\\" + "<interface\\" + ">\\"
				+ ">\\nIInterface| }\"];//writing relations between classes nowClassWithInterface -> IInterface [arrowhead = \"empty\", style = \"dashed\"];}";

		assertEquals(gv, file);

		buffer.close();
		reader.close();
	}

	@Test
	public final void testInterfaceWithSuperClass() throws IOException {
		String[] classes = { "problem.test.classes.IInterfaceWithSuperClass", "problem.test.classes.IInterface" };
		parser.parse(classes);
		FileReader reader = new FileReader("input_output/test.gv");
		BufferedReader buffer = new BufferedReader(reader);

		String file = "";
		String line = "";
		while ((line = buffer.readLine()) != null) {
			file += line;
		}

		String gv = "digraph model{rankdir = BT;IInterfaceWithSuperClass [shape=\"record\",label = \"{\\" + "<\\"
				+ "<interface\\" + ">\\"
				+ ">\\nIInterfaceWithSuperClass| }\"];IInterface [shape=\"record\",label = \"{\\" + "<\\"
				+ "<interface\\" + ">\\"
				+ ">\\nIInterface| }\"];//writing relations between classes nowIInterfaceWithSuperClass -> IInterface [arrowhead = \"empty\"];}";

		assertEquals(gv, file);

		buffer.close();
		reader.close();
	}

	@Test
	public final void testClassWithEverything() throws IOException {
		String[] classes = { "problem.test.classes.ClassWithEverything", "problem.test.classes.EmptyClass",
				"problem.test.classes.IInterface" };
		parser.parse(classes);
		FileReader reader = new FileReader("input_output/test.gv");
		BufferedReader buffer = new BufferedReader(reader);

		String file = "";
		String line = "";
		while ((line = buffer.readLine()) != null) {
			file += line;
		}

		String gv = "digraph model{rankdir = BT;ClassWithEverything [shape=\"record\",label = \"{ClassWithEverything| - name: String\\"
				+ "l|+ init() : void\\" + "l+ setName(String) : void\\" + "l+ getName() : String\\"
				+ "l}\"];EmptyClass [shape=\"record\",label = \"{EmptyClass| + init() : void\\"
				+ "l}\"];IInterface [shape=\"record\",label = \"{\\" + "<\\" + "<interface\\" + ">\\"
				+ ">\\nIInterface| }\"];//writing relations between classes nowClassWithEverything -> IInterface [arrowhead = \"empty\", style = \"dashed\"];ClassWithEverything -> EmptyClass [arrowhead = \"empty\"];}";

		assertEquals(gv, file);

		buffer.close();
		reader.close();
	}

	@Test
	public final void testClassWithUses() throws IOException {
		String[] classes = { "problem.test.classes.ClassWithUses", "problem.test.classes.EmptyClass" };
		parser.parse(classes);
		FileReader reader = new FileReader("input_output/test.gv");
		BufferedReader buffer = new BufferedReader(reader);

		String file = "";
		String line = "";

		while ((line = buffer.readLine()) != null) {
			file += line;
		}

		String gv = "digraph model{rankdir = BT;ClassWithUses [shape=\"record\",label = \"{ClassWithUses| + init(EmptyClass) : void\\"
				+ "l}\"];EmptyClass [shape=\"record\",label = \"{EmptyClass| + init() : void\\"
				+ "l}\"];//writing relations between classes nowClassWithUses -> EmptyClass [arrowhead = \"vee\", style = \"dashed\"];}";

		assertEquals(gv, file);

		buffer.close();
		reader.close();
	}

	@Test
	public final void testClassWithAssociation() throws IOException {
		String[] classes = { "problem.test.classes.ClassWithAssociation", "problem.test.classes.EmptyClass" };
		parser.parse(classes);
		FileReader reader = new FileReader("input_output/test.gv");
		BufferedReader buffer = new BufferedReader(reader);

		String file = "";
		String line = "";

		while ((line = buffer.readLine()) != null) {
			file += line;
		}

		String gv = "digraph model{rankdir = BT;ClassWithAssociation [shape=\"record\",label = \"{ClassWithAssociation| + clazz: EmptyClass\\"
				+ "l|+ init() : void\\" + "l}\"];EmptyClass [shape=\"record\",label = \"{EmptyClass| + init() : void\\"
				+ "l}\"];//writing relations between classes nowClassWithAssociation -> EmptyClass [arrowhead = \"vee\"];}";

		assertEquals(gv, file);

		buffer.close();
		reader.close();
	}

	@Test
	public final void testClassWithAssociationAndInterface() throws IOException {
		String[] classes = { "problem.test.classes.ClassWithInterfaceAndAssociation", "problem.test.classes.EmptyClass",
				"problem.test.classes.IInterface" };
		parser.parse(classes);
		FileReader reader = new FileReader("input_output/test.gv");
		BufferedReader buffer = new BufferedReader(reader);

		String file = "";
		String line = "";

		while ((line = buffer.readLine()) != null) {
			file += line;
		}
		System.out.println(file);

		String gv = "digraph model{rankdir = BT;ClassWithInterfaceAndAssociation [shape=\"record\",label = \"{ClassWithInterfaceAndAssociation| - clazz: EmptyClass\\"
				+ "l|+ init() : void\\" + "l}\"];EmptyClass [shape=\"record\",label = \"{EmptyClass| + init() : void\\"
				+ "l}\"];IInterface [shape=\"record\",label = \"{\\" + "<\\" + "<interface\\" + ">\\"
				+ ">\\nIInterface| }\"];//writing relations between classes nowClassWithInterfaceAndAssociation -> IInterface [arrowhead = \"empty\", style = \"dashed\"];ClassWithInterfaceAndAssociation -> EmptyClass [arrowhead = \"vee\"];}";

		assertEquals(gv, file);

		buffer.close();
		reader.close();
	}
	
	@Test
	public final void testModel() throws IOException {
		String[] classes = { "problem.models.impl.Model", "problem.models.api.IModel", "problem.models.api.IClass",
				"problem.models.api.IRelation",};
		parser.parse(classes);
		FileReader reader = new FileReader("input_output/test.gv");
		BufferedReader buffer = new BufferedReader(reader);

		String file = "";
		String line = "";

		while ((line = buffer.readLine()) != null) {
			file += line;
		}
		System.out.println(file);

		String gv = "digraph model{rankdir = BT;Model [shape=\"record\",label = \"{Model| - classes: IClass\\"+"l- relations: IRelation\\"+"l|+ init() : void\\"+"l+ init(IClass) : IClass\\"+"l+ getRelations() : IRelation\\"+"l+ setRelations(IRelation) : IRelation\\"+"l+ addRelation(IRelation) : void\\"+"l+ getClasses() : IClass\\"+"l+ accept(IModelVisitor) : void\\"+"l+ toString() : String\\"+"l+ addClazz(IClass) : void\\"+"l+ getClazz(String) : IClass\\"+"l}\"];IModel [shape=\"record\",label = \"{\\"+"<\\"+"<interface\\"+">\\"+">\\nIModel| + getClasses() : IClass\\"+"l+ addClazz(IClass) : void\\"+"l+ getClazz(String) : IClass\\"+"l+ getRelations() : IRelation\\"+"l}\"];IClass [shape=\"record\",label = \"{\\"+"<\\"+"<interface\\"+">\\"+">\\nIClass| + getName() : String\\"+"l+ getMethods() : IMethod\\"+"l+ getFields() : IField\\"+"l+ addMethod(IMethod) : void\\"+"l+ addField(IField) : void\\"+"l+ getIsClass() : boolean\\"+"l}\"];IRelation [shape=\"record\",label = \"{\\"+"<\\"+"<interface\\"+">\\"+">\\nIRelation| + setName(String) : void\\"+"l+ getName() : String\\"+"l+ setRelatedClass(String) : void\\"+"l+ getRelatedClass() : String\\"+"l+ setType(RelationType) : void\\"+"l+ getType() : RelationType\\"+"l}\"];//writing relations between classes nowModel -> IModel [arrowhead = \"empty\", style = \"dashed\"];Model -> IRelation [arrowhead = \"vee\"];Model -> IModelVisitor [arrowhead = \"vee\", style = \"dashed\"];Model -> IClass [arrowhead = \"vee\"];IModel -> IModelTraverser [arrowhead = \"empty\"];IModel -> IClass [arrowhead = \"vee\", style = \"dashed\"];IClass -> IModelTraverser [arrowhead = \"empty\"];IClass -> IMethod [arrowhead = \"vee\", style = \"dashed\"];IClass -> IField [arrowhead = \"vee\", style = \"dashed\"];IRelation -> IModelTraverser [arrowhead = \"empty\"];IRelation -> RelationType [arrowhead = \"vee\", style = \"dashed\"];}";
		assertEquals(gv, file);

		buffer.close();
		reader.close();
	}
}
