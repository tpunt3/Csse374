package problem.models.impl;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import problem.model.visitor.ITraverser;
import problem.model.visitor.Visitor;
import problem.model.visitor.VisitType;
import problem.models.api.IClass;
import problem.models.api.IField;
import problem.models.api.IMethod;
import problem.models.api.IModel;
import problem.models.api.IRelation;

public class ModelSDOutputStream extends FilterOutputStream{

	OutputStream out;
	StringBuilder s;
	Visitor visitor;
	
	public ModelSDOutputStream(OutputStream out) {
		super(out);
		this.out = out;
		this.visitor = new Visitor();
		this.setPreVisitModel();
		this.setVisitModel();
	}

	public void write(IModel m) {
		ITraverser t = (ITraverser) m;
		t.accept(this.visitor);
	}
	
	private void write(String s) {
		try {
			out.write(s.getBytes());
		} catch (IOException e) {
			new RuntimeException(e);
		}
	}
	
	private void setPreVisitModel() {
		this.visitor.addVisit(VisitType.PreVisit, IModel.class, (ITraverser t) ->{
			IModel m = (IModel) t;
			Object [] classes = m.getClassStrings().toArray();
			String s = "";
			for(int i = m.getClassStrings().size() - 1; i >= 0; i--){
				s += classes[i]+"\n";
			}
			
			s +="\n";
			this.write(s);
		});
	}

	private void setVisitModel() {
		this.visitor.addVisit(VisitType.Visit, IModel.class, (ITraverser t) ->{
			IModel m = (IModel) t;
			String s = "";
			for(int i = 0; i < m.getMethodStrings().size(); i++){
				s += m.getMethodStrings().get(i).toString()+"\n";
			}
			this.write(s);
		});
	}
}
