package problem.test.tests;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import problem.asm.ClassDeclarationVisitor;
import problem.asm.ClassFieldVisitor;
import problem.asm.ClassMethodVisitor;
import problem.model.visitor.IModelTraverser;
import problem.model.visitor.IModelVisitor;
import problem.models.api.IRelation;
import problem.models.impl.Model;
import problem.models.impl.ModelGVOutputStream;
import problem.models.impl.Relation;

public class TestDesignParser {
	
	public static String[] CLASSES;
	
	public TestDesignParser(){
		
	}

	public void parse(String[] CLASSES) throws IOException {

		Model model = new Model();

		for (String className : CLASSES)

		{
			TestDesignParser.CLASSES = CLASSES;
			// ASM's ClassReader does the heavy lifting of parsing the compiled
			// Java class
			ClassReader reader = new ClassReader(className);

			// make class declaration visitor to get superclass and interfaces
			ClassVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, model);

			// DECORATE declaration visitor with field visitor
			ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, decVisitor, model);

			// DECORATE field visitor with method visitor
			ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, model);

			// TODO: add more DECORATORS here in later milestones to accomplish
			// specific tasks
			// Tell the Reader to use our (heavily decorated) ClassVisitor to
			// visit the class
			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);

			// this is what they do in CarApp???
			OutputStream out = new FileOutputStream("input_output/test.gv");
			IModelVisitor gvWriter = new ModelGVOutputStream(out);
			IModelTraverser traverser = (IModelTraverser) model;
			traverser.accept(gvWriter);
			out.close();

			// runs DOT on our .gv file. This might need to be moved?
			 //ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c",
			 //"\"C:\\Users\\leekf\\Documents\\JUNIOR\\CSSE374\\release\\bin\\dot\" -Tpng input_output/test.gv > input_output/test.png");
			ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c",
			"\"C:\\Users\\punttj\\Desktop\\csse374\\release\\bin\\dot\" -Tpng input_output/test.gv > input_output/test.png");
			builder.redirectErrorStream(true);
			Process p = builder.start();
			String line;
			BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while (true) {
				line = r.readLine();
				if (line == null) {
					break;
				}
				System.out.println(line);
			}
		}
	}
}
