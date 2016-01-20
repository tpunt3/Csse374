package problem.sdTest.classes;

import java.io.IOException;

import problem.asm.DesignParser;
import problem.asm.DocType;

public class A {

private B b = new B();

public A(){
	
}

public static void main(String[] args) throws IOException{
	DesignParser parser= new DesignParser();
	String[] classes = {"problem.sdTest.classes.A"};
	//parser.generateDocuments(DocType.sd, "problem.sdTest.classes.A,A,a1", 7, classes);
}

public void a1(){
	b.b1();
}

}