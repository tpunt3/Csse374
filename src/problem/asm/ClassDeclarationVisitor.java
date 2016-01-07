package problem.asm;

import problem.models.api.IClass;
import problem.models.api.IRelation;
import problem.models.api.RelationType;
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

		this.clazz = new Class(name, isClass);
		IRelation r;

		// if its an interface and has another interface, there is an extends
		// relationship
		if (!isClass && interfaces.length > 0) {

			interfaces[0] = splitOnSlash(interfaces[0]);

			r = new Relation(name, interfaces[0], RelationType.superclass);
			this.model.addRelation(r);
		} else {
			// otherwise, it is an implements relationship
			for (String s : interfaces) {

				s = splitOnSlash(s);

				r = new Relation(name, s, RelationType.interfaces);
				this.model.addRelation(r);
			}

			superName = splitOnSlash(superName);

			r = new Relation(name, superName, RelationType.superclass);
			this.model.addRelation(r);
		}
		this.model.addClazz(clazz);

		super.visit(version, access, name, signature, superName, interfaces);
	}

	public IClass getClazz() {
		return this.clazz;
	}

	public String splitOnSlash(String stringToSplit) {
		String[] split = stringToSplit.split("/");
		return split[split.length - 1];
	}
}