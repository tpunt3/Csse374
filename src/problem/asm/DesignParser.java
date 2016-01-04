package problem.asm;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import problem.model.visitor.IModelTraverser;
import problem.model.visitor.IModelVisitor;
import problem.models.impl.Model;
import problem.models.impl.ModelGVOutputStream;

public class DesignParser {
	
	public static final String[] CLASSES= {
		"problem.asm.ClassDeclarationVisitor",
		"problem.asm.ClassFieldVisitor",
		"problem.asm.ClassMethodVisitor",
		"problem.asm.DesignParser",
		"problem.asm.FirstASM",
		"problem.asm.IClazzGetter",
		"problem.model.visitor.IModelTraverser",
		"problem.model.visitor.IModelVisitor",
		"problem.model.visitor.ModelVisitorAdapter",
		"problem.models.api.IClass",
		"problem.models.api.IField",
		"problem.models.api.IMethod",
		"problem.models.api.IModel",
		"problem.models.api.IRelation",
		"problem.models.impl.Class",
		"problem.models.impl.Field",
		"problem.models.impl.Method",
		"problem.models.impl.Model",
		"problem.models.impl.ModelGVOutputStream",
		"problem.models.impl.Relation"
	};
	
	/**
	 * Reads in a list of Java Classes and reverse engineers their design.
	 *
	 * @param args:
	 *            the names of the classes, separated by spaces. For example:
	 *            java DesignParser java.lang.String
	 *            edu.rosehulman.csse374.ClassFieldVisitor java.lang.Math
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		
		Model model = new Model();
		
		for (String className : CLASSES) {
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
			
			//this is what they do in CarApp???
			OutputStream out = new FileOutputStream("input_output/model.gv");
			IModelVisitor gvWriter = new ModelGVOutputStream(out);
			IModelTraverser traverser = (IModelTraverser)model;
			traverser.accept(gvWriter);
			out.close();
			
			//runs DOT on our .gv file. This might need to be moved?
			ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "\"C:\\Users\\leekf\\Documents\\JUNIOR\\CSSE374\\release\\bin\\dot\" -Tpng input_output/model.gv > input_output/graph1.png");
			//ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "\"C:\\Users\\punttj\\Desktop\\csse374\\release\\bin\\dot\" -Tpng input_output/model.gv > input_output/graph1.png");
			builder.redirectErrorStream(true);
			Process p = builder.start();
			String line;
			BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
	        while (true) {
	            line = r.readLine();
	            if (line == null) { break; }
	            System.out.println(line);
	        }
		}
	}
}