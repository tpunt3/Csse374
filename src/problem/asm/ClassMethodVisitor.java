package problem.asm;

import java.util.ArrayList;

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
		String args = addArguments(desc);
		// TODO: add the current method to your internal representation of the
		// current class
		// What is a good way for the code to remember what the current class
		// is?
		
		this.clazz = this.getClazz();
		IClass current = this.model.getClazz(this.clazz.getName());
		Method m = new Method(accessLevel, name, args, returnType);
		current.addMethod(m);
		
		return toDecorate;
	}

	String addAccessLevel(int access) {
		String level = "";
		if ((access & Opcodes.ACC_PUBLIC) != 0) {
			//public
			level = "+";
		} else if ((access & Opcodes.ACC_PROTECTED) != 0) {
			//protected
			level = "#";
		} else if ((access & Opcodes.ACC_PRIVATE) != 0) {
			//private
			level = "-";
		} else {
			//default
			level = "~";
		}
		return level;
	}

	String addReturnType(String desc) {
		String returnType = Type.getReturnType(desc).getClassName();
		return returnType;
	}

	String addArguments(String desc) {
		String argList = "";
		Type[] args = Type.getArgumentTypes(desc);
		for (int i = 0; i < args.length; i++) {
			String arg = args[i].getClassName();
			String[] splitArg = arg.split("\\.");
			arg = splitArg[splitArg.length-1];
			if(args.length > 1 && i < args.length -1){
				argList += arg + "; ";
			} else{
				argList += arg;
			}
		}
		return argList;
	}

	@Override
	public IClass getClazz() {
		if(decorated instanceof IClazzGetter){
			return ((IClazzGetter) decorated).getClazz();
		}
		return this.clazz;
	}
}