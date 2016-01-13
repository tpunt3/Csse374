package problem.asm;

import java.util.Collection;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import problem.models.api.IClass;
import problem.models.api.IRelation;
import problem.models.api.ISubMethod;
import problem.models.api.RelationType;
import problem.models.impl.Method;
import problem.models.impl.Model;
import problem.models.impl.Relation;
import problem.models.impl.SubMethod;

public class ClassMethodVisitor extends ClassVisitor implements IClazzGetter {
	private Model model;
	private ClassVisitor decorated;
	private IClass clazz;
	private String signature;

	public ClassMethodVisitor(int api) {
		super(api);
		this.clazz = null;
	}

	public ClassMethodVisitor(int api, ClassVisitor decorated, Model m) {
		super(api, decorated);
		this.model = m;
		this.decorated = decorated;
		this.clazz = null;
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		MethodVisitor toDecorate = super.visitMethod(access, name, desc, signature, exceptions);

		this.signature = signature;
		this.clazz = this.getClazz();
		IClass current = this.model.getClazz(this.clazz.getName());
		MethodVisitor mine = new MyMethodVisitor(Opcodes.ASM5, toDecorate, this.model, this.clazz);

		String accessLevel;

		accessLevel = addAccessLevel(access);
		String returnType = addReturnType(desc);

		String args = "";
		try {
			args = addArguments(desc);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		String[] splitArgs = args.split("; ");

		for (String s : splitArgs) {
			if (s != "") {
				IRelation r = new Relation(this.clazz.getName(), s, RelationType.uses);
				this.model.addRelation(r);
			}
		}

		if (signature != null) {
			String[] sig = signature.split("/");
			returnType = sig[sig.length - 1];
			int index = returnType.indexOf(";");
			returnType = returnType.substring(0, index);
		}

		name = name.replace("<", "");
		name = name.replace(">", "");

		Method m = new Method(accessLevel, name, args, returnType, this.clazz, ((MyMethodVisitor)mine).getSubMethods());
		current.addMethod(m);

		return mine;
	}

	String addAccessLevel(int access) {
		String level = "";
		if ((access & Opcodes.ACC_PUBLIC) != 0) {
			// public
			level = "+";
		} else if ((access & Opcodes.ACC_PROTECTED) != 0) {
			// protected
			level = "#";
		} else if ((access & Opcodes.ACC_PRIVATE) != 0) {
			// private
			level = "-";
		} else {
			// default
			level = "~";
		}
		return level;
	}

	String addReturnType(String desc) {
		String returnType = Type.getReturnType(desc).getClassName();
		return returnType;
	}

	String addArguments(String desc) throws ClassNotFoundException {
		String argList = "";
		String arg;
		Type[] args = Type.getArgumentTypes(desc);
		for (int i = 0; i < args.length; i++) {
			// if instance of Collection, use signature
			Class<?> cls = null;
			if (args[i].getClassName().startsWith("java.util."))
			{
				cls = Class.forName(args[i].getClassName());
			}
			if (cls != null && Collection.class.isAssignableFrom(cls)) {
				String[] sig = this.signature.split("/");
				arg = sig[sig.length - 1];
				int index = arg.indexOf(";");
				arg = arg.substring(0, index);
			} else {
				arg = args[i].getClassName();
				String[] splitArg = arg.split("\\.");
				arg = splitArg[splitArg.length - 1];
			}

			if (args.length > 1 && i < args.length - 1) {
				argList += arg + "; ";
			} else {
				argList += arg;
			}
		}
		return argList;
	}

	@Override
	public IClass getClazz() {
		if (decorated instanceof IClazzGetter) {
			return ((IClazzGetter) decorated).getClazz();
		}
		return this.clazz;
	}
}