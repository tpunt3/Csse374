package problem.test.tests;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

import problem.asm.DesignParser;
import problem.asm.DocType;

public class ProjectSDTesting {

	@Test
	public void testClassAccept() throws IOException {
		DesignParser parser= new DesignParser();
		String[] classes = {"problem.models.impl.Class"};
		parser.generateDocuments(DocType.sd, "problem.asm.Class,Class,accept,IModelVisitor", 5, classes);
		
		FileReader reader = new FileReader("input_output/sequence.sd");
		BufferedReader buffer = new BufferedReader(reader);
		
		String file = "";
		String line = "";

		while ((line = buffer.readLine()) != null) {
			file += line;
		}
		
		assertTrue(file.contains("Class:Class[a]"));
		assertTrue(file.contains("IModelVisitor:IModelVisitor[a]"));
		assertTrue(file.contains("IField:IField[a]"));
		assertTrue(file.contains("IMethod:IMethod[a]"));
		assertTrue(file.contains("Collection:Collection[a]"));
		
		assertTrue(file.contains("Class:IModelVisitor.preVisit(IClass)"));
		assertTrue(file.contains("Class:IModelVisitor.visit(IClass)"));
		assertTrue(file.contains("Class:IField.accept(IModelVisitor)"));
		assertTrue(file.contains("Class:Collection.isEmpty()"));
		assertTrue(file.contains("Class:IModelVisitor.intermediateVisit(IClass)"));
		assertTrue(file.contains("Class:IMethod.accept(IModelVisitor)"));
		assertTrue(file.contains("Class:IModelVisitor.postVisit(IClass)"));
		
	}

}
