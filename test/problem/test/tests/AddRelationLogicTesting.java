package problem.test.tests;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.Before;
import org.junit.Test;

import problem.model.visitor.ITraverser;
import problem.model.visitor.IVisitor;
import problem.models.api.IClass;
import problem.models.api.IRelation;
import problem.models.api.RelationType;
import problem.models.impl.Model;
import problem.models.impl.ModelGVOutputStream;
import problem.models.impl.Relation;
import problem.models.impl.Class;

public class AddRelationLogicTesting {
	
	private Model testModel;
	private IClass class1;
	private IClass class2;
	private IClass class3;
	private IClass class4;
	private IClass class5;
	private IClass class6;
	private IClass class7;
	//private IClass class8;
	
	
	@Before
	public void setUp(){
		testModel = Model.getInstance();
		class1 = new Class("CoreClass", true);
		class2 = new Class("SuperClass", true);
		class3 = new Class("Interface", false);
		class4 = new Class("AssociatedClass", true);
		class5 = new Class("UsedClass", true);
		class6 = new Class("AssociatedThenUsedClass", true);
		class7 = new Class("UsedThenAssociatedClass", true);
		
		testModel.addClazz(class1);
		testModel.addClazz(class2);
		testModel.addClazz(class3);
		testModel.addClazz(class4);
		testModel.addClazz(class5);
	}

	@Test
	public void testAddNormalRelations() throws IOException {

		IRelation superRel = new Relation(class1.getName(), class2.getName(), RelationType.superclass);
		IRelation interfaceRel = new Relation(class1.getName(), class3.getName(), RelationType.interfaces);
		IRelation associatedRel = new Relation(class1.getName(), class4.getName(), RelationType.association);
		IRelation usesRel = new Relation(class1.getName(), class5.getName(), RelationType.uses);
		
		
		testModel.addRelation(superRel);
		testModel.addRelation(interfaceRel);
		testModel.addRelation(associatedRel);
		testModel.addRelation(usesRel);

		OutputStream out = new FileOutputStream("input_output/unit_test.gv");
		ModelGVOutputStream gvWriter = new ModelGVOutputStream(out);
		ITraverser traverser = (ITraverser)testModel;
		//traverser.accept(gvWriter);
		gvWriter.write(testModel);
		out.close();
		
		FileReader reader = new FileReader("input_output/unit_test.gv");
		BufferedReader buffer = new BufferedReader(reader);

		String file = "";
		String line = "";

		while ((line = buffer.readLine()) != null) {
			file += line;
		}
		
		buffer.close();
		
		assertTrue(file.contains("CoreClass -> SuperClass [arrowhead = \"empty\"];"));
		assertTrue(file.contains("CoreClass -> Interface [arrowhead = \"empty\", style = \"dashed\"];"));
		assertTrue(file.contains("CoreClass -> AssociatedClass [arrowhead = \"vee\"];"));
		assertTrue(file.contains("CoreClass -> UsedClass [arrowhead = \"vee\", style = \"dashed\"];"));
	
		//assertTrue(file.contains(""));
	}
	
	@Test 
	public void testAddRecursiveRelation() throws IOException{
		IRelation toSelfRel = new Relation(class1.getName(), class1.getName(), RelationType.uses);
		testModel.addRelation(toSelfRel);
		
		OutputStream out = new FileOutputStream("input_output/unit_test.gv");
		ModelGVOutputStream gvWriter = new ModelGVOutputStream(out);
		ITraverser traverser = (ITraverser)testModel;
		//traverser.accept(gvWriter);
		gvWriter.write(testModel);
		out.close();
		
		FileReader reader = new FileReader("input_output/unit_test.gv");
		BufferedReader buffer = new BufferedReader(reader);

		String file = "";
		String line = "";

		while ((line = buffer.readLine()) != null) {
			file += line;
		}
		
		buffer.close();
		
		assertFalse(file.contains("CoreClass -> CoreClass"));
	}

	@Test
	public void testAddUsesAndAssociation() throws IOException{
		IRelation associatedRel2 = new Relation(class1.getName(), class6.getName(), RelationType.association);
		IRelation associatedToUsesRel = new Relation(class1.getName(), class6.getName(), RelationType.uses);
		
		IRelation usesRel2 = new Relation(class1.getName(), class7.getName(), RelationType.uses);
		IRelation usesToAssociationRel = new Relation(class1.getName(), class7.getName(), RelationType.association);
		
		testModel.addRelation(associatedRel2);
		testModel.addRelation(usesRel2);
		testModel.addRelation(associatedToUsesRel);
		testModel.addRelation(usesToAssociationRel);
		

		OutputStream out = new FileOutputStream("input_output/unit_test.gv");
		ModelGVOutputStream gvWriter = new ModelGVOutputStream(out);
		ITraverser traverser = (ITraverser)testModel;
		//traverser.accept(gvWriter);
		gvWriter.write(testModel);
		out.close();
		
		FileReader reader = new FileReader("input_output/unit_test.gv");
		BufferedReader buffer = new BufferedReader(reader);

		String file = "";
		String line = "";

		while ((line = buffer.readLine()) != null) {
			file += line;
		}
		
		buffer.close();

		assertTrue(file.contains("CoreClass -> AssociatedThenUsedClass [arrowhead = \"vee\"];"));
		assertTrue(file.contains("CoreClass -> UsedThenAssociatedClass [arrowhead = \"vee\"];"));
	}
	
	@Test 
	public void testDuplicateRelation() throws IOException{
		IRelation firstRel = new Relation(class1.getName(), class2.getName(), RelationType.superclass);
		IRelation duplicateRel = new Relation(class1.getName(), class2.getName(), RelationType.superclass);
		
		testModel.addRelation(firstRel);
		testModel.addRelation(duplicateRel);
		
		OutputStream out = new FileOutputStream("input_output/unit_test.gv");
		ModelGVOutputStream gvWriter = new ModelGVOutputStream(out);
		ITraverser traverser = (ITraverser)testModel;
		//traverser.accept(gvWriter);
		gvWriter.write(testModel);
		out.close();
		
		FileReader reader = new FileReader("input_output/unit_test.gv");
		BufferedReader buffer = new BufferedReader(reader);

		String file = "";
		String line = "";

		while ((line = buffer.readLine()) != null) {
			file += line;
		}
		
		buffer.close();
		
		assertFalse(file.contains("CoreClass -> SuperClass [arrowhead = \"empty\"];\\nCoreClass -> SuperClass [arrowhead = \"empty\"];"));
	}
}
