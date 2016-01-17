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
			// System.out.println("Description: "+desc);
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
		}

		name = name.replace("<", "");
		name = name.replace(">", "");

		Method m = new Method(accessLevel, name, this.argList, returnType, this.clazz,
				((MyMethodVisitor) mine).getSubMethods());
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

	void addArguments(String desc) throws ClassNotFoundException {
		String arg = null;
		this.argList = "";
		Type[] args = Type.getArgumentTypes(desc);
		for (int i = 0; i < args.length; i++) {
			// if (this.name.equals("shuffle"))
			// System.out.println("WOOO" + args[i].getClassName());
			// if instance of Collection, use signature
			Class<?> cls = null;
			// System.out.println("Args: "+ args[i].getClassName());
			if (args[i].getClassName().startsWith("java.util.")) {
				cls = Class.forName(args[i].getClassName());
			}
			// maybe missing some nested structures
			if (cls != null && Collection.class.isAssignableFrom(cls) && this.signature != null) {
				// System.out.println(signature);
				// if (this.name.equals("shuffle"))
				// System.out.println("IS THIS A CRAZY SIG?" + this.signature);
				String[] sigs = this.signature.split(";");
				System.out.println(this.signature);
				for (int j = 0; j < sigs.length - 1; j++) {
					if (!sigs[j].equals(">") && !sigs[j].equals("TT")) {
						String[] sig = sigs[j].split("/");
						arg = sig[sig.length - 1];
						if (arg.contains("<")) {
							arg = arg.substring(0, arg.indexOf("<"));
						}
						// if (this.name.equals("shuffle"))
						// System.out.println("adding arg: " + arg);
						if (j < sigs.length - 2) {
							addArgToList(arg, false);
						} else {
							addArgToList(arg, true);
						}
					}
				}
			} else {
				if (this.signature == null) {
					// if (this.name.equals("shuffle"))
					// System.out.println("splitting " +
					// args[i].getClassName());
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

		// if (this.name.equals("shuffle"))
		// System.out.println("ARG LIST!!" + this.argList);
	}

	private void addArgToList(String arg, boolean isLast) {
		System.out.println("adding " + arg);
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