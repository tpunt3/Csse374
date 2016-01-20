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
		
		assertTrue(file.contains("Class:void=IModelVisitor.preVisit(IClass)"));
		assertTrue(file.contains("Class:void=IModelVisitor.visit(IClass)"));
		assertTrue(file.contains("Class:void=IField.accept(IModelVisitor)"));
		assertTrue(file.contains("Class:boolean=Collection.isEmpty()"));
		assertTrue(file.contains("Class:void=IModelVisitor.intermediateVisit(IClass)"));
		assertTrue(file.contains("Class:void=IMethod.accept(IModelVisitor)"));
		assertTrue(file.contains("Class:void=IModelVisitor.postVisit(IClass)"));
		
	}

}
