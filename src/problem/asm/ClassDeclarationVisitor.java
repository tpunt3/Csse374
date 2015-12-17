package problem.asm;

import java.util.Arrays;

import problem.models.api.IClass;
import problem.models.impl.Class;
import problem.models.impl.Model;

import org.objectweb.asm.ClassVisitor;

public class ClassDeclarationVisitor extends ClassVisitor implements IClazzGetter {
	private Model model;
	private IClass clazz;
	
	public ClassDeclarationVisitor(int api, Model m) {
		super(api);
		this.model = m;
	}

	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		// TODO: delete the line below
		System.out.println("Class: " + name + " extends " + superName + " implements " + Arrays.toString(interfaces));
		
		System.out.println("-----------------------------");
		// TODO: construct an internal representation of the class for later use
		// by decorators
		
		// TODO: ...
		this.clazz = new Class(name);
		this.model.addClazz(clazz);
		
		super.visit(version, access, name, signature, superName, interfaces);
	}
	
	public IClass getClazz() {
		return this.clazz;
	}
}