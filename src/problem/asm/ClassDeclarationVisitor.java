package problem.asm;

import java.util.Arrays;
import problem.models.impl.Class;
import org.objectweb.asm.ClassVisitor;

public class ClassDeclarationVisitor extends ClassVisitor {
	public ClassDeclarationVisitor(int api) {
		super(api);
	}

	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		// TODO: delete the line below
		System.out.println("Class: " + name + " extends " + superName + " implements " + Arrays.toString(interfaces));
		
		System.out.println("-----------------------------");
		// TODO: construct an internal representation of the class for later use
		// by decorators
		
		Class c = new Class(name);
		super.visit(version, access, name, signature, superName, interfaces);
	}
}