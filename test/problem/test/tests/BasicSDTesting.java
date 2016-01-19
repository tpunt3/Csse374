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
		DesignParser parser = new DesignParser();
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
		assertFalse(file.contains("D:D[a]"));
		
		assertTrue(file.contains("A:B.b1()"));
		assertTrue(file.contains("B:C.c1()"));

		buffer.close();
		reader.close();
	}
	
	@Test
	public void testMediumDepth() throws IOException {
		DesignParser parser= new DesignParser();
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
		
		assertTrue(file.contains("A:B.b1()"));
		assertTrue(file.contains("B:C.c1()"));
		assertTrue(file.contains("C:D.d1()"));
		assertTrue(file.contains("D:E.e1()"));

		buffer.close();
		reader.close();
	}
	
	@Test 
	public void testBigDepth() throws IOException {
		DesignParser parser= new DesignParser();
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
		
		assertTrue(file.contains("A:B.b1()"));
		assertTrue(file.contains("B:C.c1()"));
		assertTrue(file.contains("C:D.d1()"));
		assertTrue(file.contains("D:E.e1()"));
		assertTrue(file.contains("E:F.f1()"));

		buffer.close();
		reader.close();
		
	}

}
