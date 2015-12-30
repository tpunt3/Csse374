package problem.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import problem.models.api.IClass;
import problem.models.impl.Method;
import problem.models.impl.Model;

public class ClassMethodVisitor extends ClassVisitor implements IClazzGetter {
	private Model model;
	private ClassVisitor decorated;
	private IClass clazz;
	
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
		String accessLevel;
		
		// TODO: create an internal representation of the current method and
		// pass it to the methods below
		accessLevel = addAccessLevel(access);
		String returnType = addReturnType(desc);
		addArguments(desc);
		// TODO: add the current method to your internal representation of the
		// current class
		// What is a good way for the code to remember what the current class
		// is?
		
		this.clazz = this.getClazz();
		IClass current = this.model.getClazz(this.clazz.getName());
		Method m = new Method(returnType, accessLevel, name);
		current.addMethod(m);
		
		return toDecorate;
	}

	String addAccessLevel(int access) {
		String level = "";
		if ((access & Opcodes.ACC_PUBLIC) != 0) {
			level = "public";
		} else if ((access & Opcodes.ACC_PROTECTED) != 0) {
			level = "protected";
		} else if ((access & Opcodes.ACC_PRIVATE) != 0) {
			level = "private";
		} else {
			level = "default";
		}
		return level;
	}

	String addReturnType(String desc) {
		String returnType = Type.getReturnType(desc).getClassName();
		return returnType;
	}

	void addArguments(String desc) {
		Type[] args = Type.getArgumentTypes(desc);
		for (int i = 0; i < args.length; i++) {
			String arg = args[i].getClassName();
			// TODO: delete the next line
//			System.out.println("arg " + i + ": " + arg);
			// TODO: ADD this information to your representation of the current
			// method.
		}
	}

	@Override
	public IClass getClazz() {
		if(decorated instanceof IClazzGetter){
			return ((IClazzGetter) decorated).getClazz();
		}
		return this.clazz;
	}
}