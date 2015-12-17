package problem.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import problem.models.impl.Field;

public class ClassFieldVisitor extends ClassVisitor {
	public ClassFieldVisitor(int api) {
		super(api);
	}

	public ClassFieldVisitor(int api, ClassVisitor decorated) {
		super(api, decorated);
	}

	public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
		FieldVisitor toDecorate = super.visitField(access, name, desc, signature, value);
		String type = Type.getType(desc).getClassName();
		// TODO: delete the line below
		System.out.println(" " + type + " " + name);
		// TODO: add this field to your internal representation of the current
		// class.
		// What is a good way to know what the current class is?
		String accessLevel;
		accessLevel = addAccessLevel(access);
		
		Field f = new Field(type,accessLevel);
		
		return toDecorate;
	};
	
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
		
		// TODO: delete the next line
		System.out.println("access level: " + level);
		// TODO: ADD this information to your representation of the current
		// method.
		
		return level;
	}
}