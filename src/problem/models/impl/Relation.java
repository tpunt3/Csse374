package problem.models.impl;

import java.util.HashMap;
import java.util.Map;

import problem.model.visitor.IModelVisitor;
import problem.models.api.IRelation;

public class Relation implements IRelation{
	
	private Map<String, String> subclassToSuperclass = new HashMap<String, String>();
	private Map<String, String[]> subclassToInterface = new HashMap<String, String[]>();
	
	private String superClass;
	private String[] interfaces;

	public Relation(){
		
	}
	
	public Relation(String superClass) {
		this.superClass = superClass;
	}
	
	public Relation(String[] interfaces){
		this.interfaces = interfaces;
	}
	
	public Relation(String superClass, String[] i) {
		this.superClass = superClass;
		this.interfaces = i;
	}
	
	@Override
	public String getSuperClass() {
		return this.superClass;
	}

//	@Override
//	public String[] getInterfaces() {
//		return this.interfaces;
//	}

	@Override
	public void accept(IModelVisitor v) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, String> getSuperClasses() {
		return this.subclassToSuperclass;
	}
	
	public void addSuperClass(String key, String value){
		this.subclassToSuperclass.put(key, value);
	}

	@Override
	public void addInterfaces(String name, String[] interfaceName) {
		this.subclassToInterface.put(name, interfaceName);
	}
	
	public Map<String, String[]> getInterfaces() {
		return subclassToInterface;
	}

	public void setSubclassToInterface(Map<String, String[]> subclassToInterface) {
		this.subclassToInterface = subclassToInterface;
	}


}
