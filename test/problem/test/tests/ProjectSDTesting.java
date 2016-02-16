package problem.test.tests;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

import problem.asm.DesignParser;
import problem.asm.DocType;
import problem.models.api.ISubMethod;
import problem.models.impl.Model;

public class ProjectSDTesting {

	@Test
	public void testClassAccept() throws IOException {
		 DesignParser parser= new DesignParser("\"C:\\Users\\punttj\\Desktop\\csse374\\release\\bin\\dot\"","\"C:\\Users\\punttj\\Desktop\\csse374\\finalProject\\sdedit-4.2-beta1.exe\"");
		//DesignParser parser = new DesignParser("\"C:\\Users\\leekf\\Documents\\JUNIOR\\CSSE374\\release\\bin\\dot\"",
				//"\"C:\\Users\\leekf\\Documents\\JUNIOR\\CSSE374\\sdedit-4.2-beta1.exe\"");
		String[] classes = { "problem.models.impl.Class" };
		ISubMethod sm = parser.setUpSD("problem.models.impl.Class,Class,accept,IVisitor");
		Model m = parser.visitClasses(classes);
		parser.generateSD(parser.getPathToSD(), m, sm, 5);
		//parser.generateDocuments(DocType.sd, "problem.models.impl.Class,Class,accept,IVisitor", 5, classes);

		FileReader reader = new FileReader("input_output/sequence.sd");
		BufferedReader buffer = new BufferedReader(reader);

		String file = "";
		String line = "";

		while ((line = buffer.readLine()) != null) {
			file += line;
		}

		assertTrue(file.contains("Class:Class[a]"));
		assertTrue(file.contains("IVisitor:IVisitor[a]"));
		assertTrue(file.contains("IField:IField[a]"));
		assertTrue(file.contains("IMethod:IMethod[a]"));
		assertTrue(file.contains("Collection:Collection[a]"));
		assertTrue(file.contains("HashSet:HashSet[a]"));
		assertTrue(file.contains("Iterator:Iterator[a]"));
		assertTrue(file.contains("Set:Set[a]"));
		assertTrue(file.contains("HashMap:HashMap[a]"));

		assertTrue(file.contains("Class:void=IVisitor.preVisit(ITraverser)"));
		assertTrue(file.contains("Class:void=IVisitor.visit(ITraverser)"));
		assertTrue(file.contains("Class:Iterator=Collection.iterator()"));
		assertTrue(file.contains("Class:Object=Iterator.next()"));
		assertTrue(file.contains("Class:void=IField.accept(IVisitor)"));
		assertTrue(file.contains("Class:boolean=Iterator.hasNext()"));
		assertTrue(file.contains("Class:boolean=Collection.isEmpty()"));
		assertTrue(file.contains("Class:void=IVisitor.intermediateVisit(ITraverser)"));
		assertTrue(file.contains("Class:Iterator=HashSet.iterator()"));
		assertTrue(file.contains("HashSet:Set=HashMap.keySet()"));
		assertTrue(file.contains("HashSet:Iterator=Set.iterator()"));
		assertTrue(file.contains("Class:Object=Iterator.next()"));
		assertTrue(file.contains("Class:void=IMethod.accept(IVisitor)"));
		assertTrue(file.contains("Class:boolean=Iterator.hasNext()"));
		assertTrue(file.contains("Class:void=IVisitor.postVisit(ITraverser)"));

	}

}
