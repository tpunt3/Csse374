package problem.asm;

import java.util.Arrays;

import problem.models.api.IClass;
import problem.models.api.IRelation;
import problem.models.impl.Class;
import problem.models.impl.Model;
import problem.models.impl.Relation;

import org.objectweb.asm.ClassVisitor;

public class ClassDeclarationVisitor extends ClassVisitor implements IClazzGetter {
	private Model model;
	private IClass clazz;
	
	public ClassDeclarationVisitor(int api, Model m) {
		super(api);
		this.model = m;
		this.clazz = new Class();
	}

	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		
		// TODO: construct an internal representation of the class for later use
		// by decorators
		
		this.clazz = new Class(name);
		IRelation r = new Relation(superName, interfaces);
		this.clazz.addRelation(r);
		this.model.addClazz(clazz);
		
		super.visit(version, access, name, signature, superName, interfaces);
	}
	
	public IClass getClazz() {
		return this.clazz;
	}
}