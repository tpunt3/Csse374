package problem.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import problem.models.api.IClass;
import problem.models.impl.Field;
import problem.models.impl.Model;

public class ClassFieldVisitor extends ClassVisitor implements IClazzGetter{
	private Model model;
	private IClass clazz;
	private ClassVisitor decorated;
	
	public ClassFieldVisitor(int api, ClassVisitor decorated, Model m) {
		super(api, decorated);
		this.model = m;
		this.clazz = null;
		this.decorated = decorated;
	}

	public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
		
		FieldVisitor toDecorate = super.visitField(access, name, desc, signature, value);
		String type = Type.getType(desc).getClassName();
		String[] typeSplit = type.split("\\.");
		type = typeSplit[typeSplit.length-1];
		String accessLevel;
		accessLevel = addAccessLevel(access);
		
		this.clazz = this.getClazz();
		
		if (signature != null) {
			String[] sig = signature.split("/");
			type = sig[sig.length - 1];
			int index = type.indexOf(";");
			type = type.substring(0, index);
		}
		
		IClass current = this.model.getClazz(this.clazz.getName());
		Field f = new Field(type, name, accessLevel);
		current.addField(f);
		
		return toDecorate;
	};
	
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
			//Default
			level = "~";
		}
		
		return level;
	}

	@Override
	public IClass getClazz() {
		if(decorated instanceof IClazzGetter){
			return ((IClazzGetter)decorated).getClazz();
		}
		return this.clazz;
	}
}