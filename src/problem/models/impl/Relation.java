package problem.models.impl;

import java.util.HashMap;
import java.util.Map;

import problem.model.visitor.IModelVisitor;
import problem.models.api.IRelation;
import problem.models.api.RelationType;

public class Relation implements IRelation {
	
	private String name;
	private String relatedClass;
	private RelationType type;

	public Relation() {

	}
	
	public Relation(String name, String relatedClass, RelationType type){
		this.name = name;
		this.relatedClass = relatedClass;
		this.type = type;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String getRelatedClass() {
		return relatedClass;
	}

	@Override
	public void setRelatedClass(String relatedClass) {
		this.relatedClass = relatedClass;
	}

	@Override
	public RelationType getType() {
		return type;
	}

	@Override
	public void setType(RelationType type) {
		this.type = type;
	}

	@Override
	public void accept(IModelVisitor v) {
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((relatedClass == null) ? 0 : relatedClass.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Relation other = (Relation) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (relatedClass == null) {
			if (other.relatedClass != null)
				return false;
		} else if (!relatedClass.equals(other.relatedClass))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
}
