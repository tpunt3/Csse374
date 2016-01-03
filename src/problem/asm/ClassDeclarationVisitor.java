package problem.asm;

import java.util.Arrays;

import problem.models.api.IClass;
import problem.models.api.IRelation;
import problem.models.impl.Class;
import problem.models.impl.Model;
import problem.models.impl.Relation;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

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

		boolean isClass = true;
		if ((access & Opcodes.ACC_INTERFACE) != 0) {
			isClass = false;
		}

		// removes everything except the last part of the class name
		String[] nameSplit = name.split("/");
		name = nameSplit[nameSplit.length - 1];

		this.clazz = new Class(name);
		IRelation r;
		if (!isClass && interfaces.length > 0) {
			r = new Relation(interfaces[0]);
			r.addSuperClass(name, interfaces[0]);
		} else {			
			r = new Relation(superName, interfaces);
			r.addSuperClass(name, superName);
			r.addInterfaces(name, interfaces);
		}
		this.clazz.addRelation(r);
		this.model.addRelation(r);
		this.model.addClazz(clazz);

		super.visit(version, access, name, signature, superName, interfaces);
	}

	public IClass getClazz() {
		return this.clazz;
	}
}