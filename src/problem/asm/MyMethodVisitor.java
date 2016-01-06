package problem.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import problem.models.api.IClass;
import problem.models.api.IRelation;
import problem.models.api.RelationType;
import problem.models.impl.Model;
import problem.models.impl.Relation;

public class MyMethodVisitor extends MethodVisitor{
	private Model model;
	private IClass clazz;
	private MethodVisitor decorated;

	public MyMethodVisitor(int api, MethodVisitor decorated, Model m) {
		super(api, decorated);
		this.model = m;
		this.clazz = null;
		this.decorated = decorated;
	}

	@Override
	public void visitFieldInsn(int opcode, String owner, String name, String desc) {
		super.visitFieldInsn(opcode, owner, name, desc);
	}

	@Override
	public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
		super.visitMethodInsn(opcode,owner, name, desc, itf);
		String[] args = addArguments(desc);
		
		String[] ownerSplit = owner.split("/");
		owner = ownerSplit[ownerSplit.length-1];
		
		IRelation r = new Relation(RelationType.uses, args);
		r.addUses(owner, args);
		this.model.addRelation(r);
		
	}

	@Override
	public void visitTypeInsn(int opcode, String type) {
		super.visitTypeInsn(opcode, type);
	}

	@Override
	public void visitVarInsn(int opcode, int var) {
		super.visitVarInsn(opcode, var);
	}
	
	String[] addArguments(String desc) {
		String argList = "";
		Type[] args = Type.getArgumentTypes(desc);
		String[] argClassList = new String[args.length];
		for (int i = 0; i < args.length; i++) {
			String arg = args[i].getClassName();
			String[] splitArg = arg.split("\\.");
			
			argClassList[i] = splitArg[splitArg.length-1];

		}
		return argClassList;
	}
	

}
