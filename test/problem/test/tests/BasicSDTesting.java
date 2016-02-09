package problem.test.tests;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

import problem.asm.DesignParser;
import problem.asm.DocType;

public class BasicSDTesting {

	@Test
	public void testSmallDepth() throws IOException {
		//DesignParser parser = new DesignParser("\"C:\\Users\\punttj\\Desktop\\csse374\\release\\bin\\dot\"","\"C:\\Users\\punttj\\Desktop\\csse374\\finalProject\\sdedit-4.2-beta1.exe\"");
		DesignParser parser = new DesignParser("\"C:\\Users\\leekf\\Documents\\JUNIOR\\CSSE374\\release\\bin\\dot\"", "\"C:\\Users\\leekf\\Documents\\JUNIOR\\CSSE374\\sdedit-4.2-beta1.exe\"");
		String[] classes = {"problem.sdTest.classes.A"};
		parser.generateDocuments(DocType.sd, "problem.sdTest.classes.A,A,a1", 2, classes);
		
		FileReader reader = new FileReader("input_output/sequence.sd");
		BufferedReader buffer = new BufferedReader(reader);

		String file = "";
		String line = "";
		while ((line = buffer.readLine()) != null) {
			file += line;
		}
		
		assertTrue(file.contains("A:A[a]"));
		assertTrue(file.contains("B:B[a]"));
		assertTrue(file.contains("C:C[a]"));
		
		assertTrue(file.contains("A:void=B.b1()"));
		assertTrue(file.contains("B:void=C.c1()"));

		buffer.close();
		reader.close();
	}
	
	@Test
	public void testMediumDepth() throws IOException, InterruptedException {
		//DesignParser parser= new DesignParser("\"C:\\Users\\punttj\\Desktop\\csse374\\release\\bin\\dot\"","\"C:\\Users\\punttj\\Desktop\\csse374\\finalProject\\sdedit-4.2-beta1.exe\"");
		DesignParser parser = new DesignParser("\"C:\\Users\\leekf\\Documents\\JUNIOR\\CSSE374\\release\\bin\\dot\"", "\"C:\\Users\\leekf\\Documents\\JUNIOR\\CSSE374\\sdedit-4.2-beta1.exe\"");
		String[] classes = {"problem.sdTest.classes.A"};
		parser.generateDocuments(DocType.sd, "problem.sdTest.classes.A,A,a1", 4, classes);
		
		FileReader reader = new FileReader("input_output/sequence.sd");
		BufferedReader buffer = new BufferedReader(reader);

		String file = "";
		String line = "";
		while ((line = buffer.readLine()) != null) {
			file += line;
		}
		
		assertTrue(file.contains("A:A[a]"));
		assertTrue(file.contains("B:B[a]"));
		assertTrue(file.contains("C:C[a]"));
		assertTrue(file.contains("D:D[a]"));
		assertTrue(file.contains("E:E[a]"));
		
		assertTrue(file.contains("A:void=B.b1()"));
		assertTrue(file.contains("B:void=C.c1()"));
		assertTrue(file.contains("C:void=D.d1()"));
		assertTrue(file.contains("D:void=E.e1()"));

		buffer.close();
		reader.close();
	}
	
	@Test 
	public void testBigDepth() throws IOException {
		//DesignParser parser= new DesignParser("\"C:\\Users\\punttj\\Desktop\\csse374\\release\\bin\\dot\"","\"C:\\Users\\punttj\\Desktop\\csse374\\finalProject\\sdedit-4.2-beta1.exe\"");
		DesignParser parser = new DesignParser("\"C:\\Users\\leekf\\Documents\\JUNIOR\\CSSE374\\release\\bin\\dot\"", "\"C:\\Users\\leekf\\Documents\\JUNIOR\\CSSE374\\sdedit-4.2-beta1.exe\"");
		String[] classes = {"problem.sdTest.classes.A"};
		parser.generateDocuments(DocType.sd, "problem.sdTest.classes.A,A,a1", 10, classes);
		
		FileReader reader = new FileReader("input_output/sequence.sd");
		BufferedReader buffer = new BufferedReader(reader);

		String file = "";
		String line = "";
		while ((line = buffer.readLine()) != null) {
			file += line;
		}
		
		assertTrue(file.contains("A:A[a]"));
		assertTrue(file.contains("B:B[a]"));
		assertTrue(file.contains("C:C[a]"));
		assertTrue(file.contains("D:D[a]"));
		assertTrue(file.contains("E:E[a]"));
		assertTrue(file.contains("F:F[a]"));
		
		assertTrue(file.contains("A:void=B.b1()"));
		assertTrue(file.contains("B:void=C.c1()"));
		assertTrue(file.contains("C:void=D.d1()"));
		assertTrue(file.contains("D:void=E.e1()"));
		assertTrue(file.contains("E:void=F.f1()"));

		buffer.close();
		reader.close();
		
	}

}
