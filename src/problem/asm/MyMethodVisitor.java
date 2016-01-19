package problem.asm;

import java.util.ArrayList;
import java.util.Collection;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import problem.models.api.IClass;
import problem.models.api.IMethod;
import problem.models.api.IRelation;
import problem.models.api.ISubMethod;
import problem.models.api.RelationType;
import problem.models.impl.Method;
import problem.models.impl.Model;
import problem.models.impl.Relation;
import problem.models.impl.SubMethod;

public class MyMethodVisitor extends MethodVisitor {
	private Model model;
	private IClass clazz;
	private ArrayList<ISubMethod> subMethods;
	private String name;

	public MyMethodVisitor(int api, MethodVisitor decorated, Model m, IClass clazz) {
		super(api, decorated);
		this.model = m;
		this.clazz = clazz;
		this.subMethods = new ArrayList<ISubMethod>();
		//System.out.println(this.clazz.);
	}

	@Override
	public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
		super.visitMethodInsn(opcode, owner, name, desc, itf);
		
		this.name = name;
		//if(name.equals("shuffle"))
			//System.out.println("DESCRIPTION: "+desc);
		
		String args = getArguments(desc);
		String qualified = owner.replace("/", ".");
		String[] ownerSplit = owner.split("/");
		owner = ownerSplit[ownerSplit.length-1];
		
		String returnType = getReturnType(desc);
		String[] returnSplit = returnType.split("\\.");
		returnType = returnSplit[returnSplit.length-1];
		
		
		ISubMethod sm = new SubMethod(qualified, owner, name, args, returnType);
		if(!(name.equals("<init>"))){
			this.subMethods.add(sm);
		}
	}

	@Override
	public void visitFieldInsn(int opcode, String owner, String name, String desc) {
		super.visitFieldInsn(opcode, owner, name, desc);

		// this clearly doesnt work as we thought it did, we now do this in
		// classFieldVisitor

		// if owner is the class we are in, then we can automatically add it as
		// an associations arrow,
		// because that is stronger than a uses

		// String[] ownerSplit = owner.split("/");
		// owner = ownerSplit[ownerSplit.length - 1];
		//
		// String fieldType = getType(desc);
		// String[] splitField = fieldType.split("\\.");
		// fieldType = splitField[splitField.length - 1];
		//
		// if (owner.equals(this.clazz.getName())) {
		// //create relation for assocation
		//
		// IRelation r = new Relation(owner, fieldType,
		// RelationType.association);
		// this.model.addRelation(r);
		// }
	}

	@Override
	public void visitTypeInsn(int opcode, String type) {
		super.visitTypeInsn(opcode, type);

		String[] typeSplit = type.split("/");
		type = typeSplit[typeSplit.length - 1];

		// create relation for uses
		IRelation r = new Relation(this.clazz.getName(), type, RelationType.uses);
		this.model.addRelation(r);
	}

	@Override
	public void visitVarInsn(int opcode, int var) {
		super.visitVarInsn(opcode, var);
	}

	String getArguments(String desc) {
		String argList = "";
		String arg;
		Type[] args = Type.getArgumentTypes(desc);
		
		for (int i = 0; i < args.length; i++) {
			arg = args[i].getClassName();
			//System.out.println(arg);
			String[] splitArg = arg.split("\\.");
			arg = splitArg[splitArg.length - 1];

			if (args.length > 1 && i < args.length - 1) {
				argList += arg + "; ";
			} else {
				argList += arg;
			}
		}
		
		return argList;
	}
	
	String getReturnType(String desc) {
		String returnType = Type.getReturnType(desc).getClassName();
		return returnType;
	}

	String getType(String desc) {
		Type type = Type.getType(desc);
		String t = type.getClassName();
		return t;
	}

	public ArrayList<ISubMethod> getSubMethods() {
		return this.subMethods;
	}
}
