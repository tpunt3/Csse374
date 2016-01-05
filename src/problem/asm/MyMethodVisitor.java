package problem.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

import problem.models.api.IClass;
import problem.models.impl.Model;

public class MyMethodVisitor extends MethodVisitor {
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
	public void visitFieldInsn(int arg0, String arg1, String arg2, String arg3) {
		// TODO Auto-generated method stub
		super.visitFieldInsn(arg0, arg1, arg2, arg3);
	}

	@Override
	public void visitMethodInsn(int arg0, String arg1, String arg2, String arg3, boolean arg4) {
		// TODO Auto-generated method stub
		super.visitMethodInsn(arg0, arg1, arg2, arg3, arg4);
	}

	@Override
	public void visitTypeInsn(int arg0, String arg1) {
		// TODO Auto-generated method stub
		super.visitTypeInsn(arg0, arg1);
	}

	@Override
	public void visitVarInsn(int arg0, int arg1) {
		// TODO Auto-generated method stub
		super.visitVarInsn(arg0, arg1);
	}
	
	

}
