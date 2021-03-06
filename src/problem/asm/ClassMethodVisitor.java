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
	private String name;
	private String argList;

	public ClassMethodVisitor(int api) {
		super(api);
		this.clazz = null;
	}

	public ClassMethodVisitor(int api, ClassVisitor decorated, Model m) {
		super(api, decorated);
		this.model = m;
		this.decorated = decorated;
		this.clazz = null;
		this.argList = "";
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {

		this.name = name;
		MethodVisitor toDecorate = super.visitMethod(access, name, desc, signature, exceptions);

		this.signature = signature;
		this.clazz = this.getClazz();
		IClass current = this.model.getClazz(this.clazz.getName());
		MethodVisitor mine = new MyMethodVisitor(Opcodes.ASM5, toDecorate, this.model, this.clazz);

		String accessLevel;

		accessLevel = addAccessLevel(access);
		String returnType = addReturnType(desc);

		try {
			addArguments(desc);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		String[] splitArgs = this.argList.split("; ");

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
			if (returnType.contains("<")) {
				returnType = returnType.substring(0, returnType.indexOf("<"));
			}

			if (signature.equals("()TE;")) {
				returnType = "E";
			}
		}

		name = name.replace("<", "");
		name = name.replace(">", "");

		if (name.contains("init")) {
			returnType = "void";
		}
		
		argList = argList.trim();
		if(argList.endsWith(";")){
			argList = argList.substring(0, argList.lastIndexOf(";"));
		}

		Method m = new Method(accessLevel, access, name, this.argList, returnType, this.clazz,
				((MyMethodVisitor) mine).getSubMethods());
		current.addMethod(m);

		return mine;
	}

	String addAccessLevel(int access) {
		String level = "";
		if ((access & Opcodes.ACC_PUBLIC) != 0) {
			// public
			level = "+";
		} else if ((access & Opcodes.ACC_STATIC) != 0) {
			level = "%";
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

	void addArguments(String desc) throws ClassNotFoundException {
		// System.out.println(this.signature);
		String arg = null;
		this.argList = "";
		Type[] args = Type.getArgumentTypes(desc);
		for (int i = 0; i < args.length; i++) {
			// if instance of Collection, use signature
			String name = args[i].getClassName();
			// System.out.println(name);
			Class<?> cls = null;
			if (args[i].getClassName().startsWith("java.util.")) {
				if (name.contains("$"))
					name = name.substring(0, name.indexOf("$"));
				cls = Class.forName(name);
			}
			if (cls != null && signature != null) {
				String[] sigs = this.signature.split(";");
				for (int j = 0; j < sigs.length - 1; j++) {
					if (!sigs[j].equals(">") && !sigs[j].equals("TT")) {
						String[] sig = sigs[j].split("/");
						arg = sig[sig.length - 1];
						if (arg.contains("<")) {
							arg = arg.substring(0, arg.indexOf("<"));
						}
						if (j < sigs.length - 2) {
							addArgToList(arg, false);
						} else {
							addArgToList(arg, true);
						}
					}
				}
			} else {
				arg = args[i].getClassName();
				String[] splitArg = arg.split("\\.");
				arg = splitArg[splitArg.length - 1];
				if (args.length > 1 && i < args.length - 1) {
					addArgToList(arg, false);
				} else {
					addArgToList(arg, true);
				}
			}
		}
	}

	private void addArgToList(String arg, boolean isLast) {
		if (!isLast) {
			argList += arg + "; ";
		} else {
			argList += arg;
		}

	}

	@Override
	public IClass getClazz() {
		if (decorated instanceof IClazzGetter) {
			return ((IClazzGetter) decorated).getClazz();
		}
		return this.clazz;
	}
}