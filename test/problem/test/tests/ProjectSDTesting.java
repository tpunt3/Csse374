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
		//DesignParser parser= new DesignParser("\"C:\\Users\\punttj\\Desktop\\csse374\\release\\bin\\dot\"","\"C:\\Users\\punttj\\Desktop\\csse374\\finalProject\\sdedit-4.2-beta1.exe\"");
		DesignParser parser = new DesignParser("\"C:\\Users\\leekf\\Documents\\JUNIOR\\CSSE374\\release\\bin\\dot\"", "\"C:\\Users\\leekf\\Documents\\JUNIOR\\CSSE374\\sdedit-4.2-beta1.exe\"");
		String[] classes = {"problem.models.impl.Class"};
		parser.generateDocuments(DocType.sd, "problem.models.impl.Class,Class,accept,IVisitor", 5, classes);
		
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
		
		assertTrue(file.contains("Class:void=IVisitor.preVisit(IClass)"));
		assertTrue(file.contains("Class:void=IVisitor.visit(IClass)"));
		assertTrue(file.contains("Class:void=IField.accept(IModelVisitor)"));
		assertTrue(file.contains("Class:boolean=Collection.isEmpty()"));
		assertTrue(file.contains("Class:void=IVisitor.intermediateVisit(IClass)"));
		assertTrue(file.contains("Class:void=IMethod.accept(IModelVisitor)"));
		assertTrue(file.contains("Class:void=IVisitor.postVisit(IClass)"));
		
	}

}
